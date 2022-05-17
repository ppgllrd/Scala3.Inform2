/*
  Pepe Gallardo, 2022
 */

package classes.graph

import scala.collection._

case class DiEdge[V](src: V, dst: V)

class DiGraph[V]() extends classes.graph.Traversable[V]:
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

  def edges: immutable.Set[DiEdge[V]] =
    var es = immutable.Set[DiEdge[V]]()
    for (src, destinations) <- map do
      for dst <- destinations do
        es = es + DiEdge(src, dst)
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



@main def graphTest(): Unit =
  val g = new DiGraph[Int]()

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
  val dft = g.depthFirstTraversal(1)
  for v <- g.vertices do
    println(dft.pathTo(v))

  println()
  val bft = g.breadthFirstTraversal(1)
  for v <- g.vertices do
    println(bft.pathTo(v))