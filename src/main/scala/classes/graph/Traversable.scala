/*
  Pepe Gallardo, 2023
 */

package classes.graph

import scala.collection.*

/**
 * Trait for structures that support graph traversals (DFS and BFS).
 * Implementors must define `successors` to provide adjacent elements.
 */
trait Traversable[T]:
  def successors(elem: T): Iterable[T]

  def depthFirstTraversal(src: T): Traversal[T] =
    val dft = DepthFirstTraversal(src, this)
    dft.traverse()
    dft

  def breadthFirstTraversal(src: T): Traversal[T] =
    val bft = BreadthFirstTraversal(src, this)
    bft.traverse()
    bft

  def withContainerTraversal(_src: T, _container: Traversal.Container[DiEdge[T]]): Traversal[T] =
    val _traversable = this
    val t: Traversal[T] = new Traversal[T]:
      val src: T = _src
      val container: Traversal.Container[DiEdge[T]] = _container
      val traversable: Traversable[T] = _traversable
    t.traverse()
    t

object Traversal:
  trait Container[T]:
    def isEmpty: Boolean
    def nonEmpty: Boolean = !isEmpty
    def add(elem: T): Unit
    def remove(): T

  class StackContainer[T] extends Container[T]:
    private val stack = mutable.Stack[T]()
    override def isEmpty: Boolean = stack.isEmpty
    override def add(elem: T): Unit = stack.push(elem)
    override def remove(): T = stack.pop()

  class QueueContainer[T] extends Container[T]:
    private val queue = mutable.Queue[T]()
    override def isEmpty: Boolean = queue.isEmpty
    override def add(elem: T): Unit = queue.enqueue(elem)
    override def remove(): T = queue.dequeue()

/** Abstract base for graph traversals. Tracks visited nodes and reconstructs paths. */
trait Traversal[T]:
  val src: T
  val traversable: Traversable[T]

  protected val container: Traversal.Container[DiEdge[T]]
  protected val sourceOf: mutable.Map[T, T] = mutable.Map[T, T]()

  private var traversed = false

  /** Explores the graph starting from `src`, recording parent edges in `sourceOf`. */
  def traverse(): Unit =
    container.add(DiEdge(src, src))
    while container.nonEmpty do
      val DiEdge(src, dst) = container.remove()
      if !sourceOf.contains(dst) then
        sourceOf(dst) = src // record where we came from
        for v <- traversable.successors(dst) do
          if !sourceOf.contains(v) then container.add(DiEdge(dst, v))
    traversed = true

  def isReachable(dst: T): Boolean =
    require(traversed, "Traversal must be traversed")
    sourceOf.isDefinedAt(dst)

  /** Reconstructs the path from `src` to `dst` by following `sourceOf` links backwards. */
  def pathTo(dst: T): List[T] =
    require(traversed, "Traversal must be traversed")
    require(isReachable(dst), s"No path to $dst from $src")
    var path = List[T](dst)
    while path.head != src do path ::= sourceOf(path.head)
    path

/** Depth-first traversal using a stack as the container. */
class DepthFirstTraversal[T](val src: T, val traversable: Traversable[T]) extends Traversal[T]:
  val container = Traversal.StackContainer[DiEdge[T]]()

/** Breadth-first traversal using a queue as the container. */
class BreadthFirstTraversal[T](val src: T, val traversable: Traversable[T]) extends Traversal[T]:
  val container = Traversal.QueueContainer[DiEdge[T]]()
