/*
  Pepe Gallardo, 2022
 */

package classes.graph

import scala.collection._

case class Edge[V](src: V, dst: V)

class Graph[V]():
  private var map = immutable.Map[V, immutable.Set[V]]()

  def addVertex(v: V): Unit =
    require(!map.contains(v), s"Vertex $v is already in graph.")
    map = map + (v -> immutable.Set[V]())

  def deleteVertex(v: V): Unit =
    require(map.contains(v), s"Vertex $v is not in graph.")
    map = map - v
    for (src, destinations) <- map do
      map = map + (src -> (destinations - v))

  def addEdge(src: V, dst: V): Unit =
    require(map.contains(src), s"Vertex $src is not in graph.")
    require(map.contains(dst), s"Vertex $dst is not in graph.")
    require(src != dst, "Self loop not allowed")

    val oldSet = map(src)
    val set = oldSet + dst
    map = map + (src -> set)

  def deleteEdge(src: V, dst: V): Unit =
    require(map.contains(src), s"Vertex $src is not in graph.")
    require(map.contains(dst), s"Vertex $dst is not in graph.")

    val oldSet = map(src)
    require(oldSet.contains(dst), s"There is no edge from $src to $dst")

    val set = oldSet - dst
    map = map + (src -> set)

  def vertices: immutable.Set[V] =
    map.keys.toSet

  def edges: immutable.Set[Edge[V]] =
    var es = immutable.Set[Edge[V]]()
    for (src, destinations) <- map do
      for dst <- destinations do
        es = es + Edge(src, dst)
    es

  def successors(v: V): immutable.Set[V] =
    map.get(v) match
      case None =>
        sys.error(s"Vertex $v is not in graph")
      case Some(set) =>
        set

  override def toString: String =
    val verticesStr = vertices.mkString("Vertices(", ", ", ")")
    val edgesStr = edges.mkString("Edges(", ", ", ")")
    s"Graph($verticesStr, $edgesStr)"


  private def pathFromTo(src: V, dst: V, sourceOf: Map[V, V]): List[V] =
    require(sourceOf.contains(dst), s"No path to vertex $dst from $src")
    var path = List[V](dst)
    while path.head != src do
      path ::= sourceOf(path.head)
    path

  class DFT(src: V):
    private val sourceOf = mutable.Map[V, V]()
    private val stack = mutable.Stack[Edge[V]]()

    stack.push(Edge(src, src))
    while stack.nonEmpty do
      val Edge(src, dst) = stack.pop()
      if !sourceOf.contains(dst) then
        sourceOf(dst) = src
        for v <- successors(dst) do
          if !sourceOf.contains(v) then
            stack.push(Edge(dst, v))

    def pathTo(v: V): List[V] =
      pathFromTo(src, v, sourceOf)

  class BFT(src: V):
    private val sourceOf = mutable.Map[V, V]()
    private val queue = mutable.Queue[Edge[V]]()

    queue.enqueue(Edge(src, src))
    while queue.nonEmpty do
      val Edge(src, dst) = queue.dequeue()
      if !sourceOf.contains(dst) then
        sourceOf(dst) = src
        for v <- successors(dst) do
          if !sourceOf.contains(v) then
            queue.enqueue(Edge(dst, v))

    def pathTo(v: V): List[V] =
      pathFromTo(src, v, sourceOf)


@main def graphTest(): Unit =
  val g = new Graph[Int]()

  g.addVertex(1)
  g.addVertex(2)
  g.addVertex(3)
  g.addVertex(4)
  g.addVertex(5)

  g.addEdge(1, 2)
  g.addEdge(1, 3)
  g.addEdge(2, 3)
  g.addEdge(3, 4)
  g.addEdge(4, 5)
  g.addEdge(5, 1)

  println(g)

  println()
  val dft = new g.DFT(1)
  for v <- g.vertices do
    println(dft.pathTo(v))

  println()
  val bft = new g.BFT(1)
  for v <- g.vertices do
    println(bft.pathTo(v))