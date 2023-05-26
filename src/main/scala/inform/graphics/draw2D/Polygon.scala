/*
  Pepe Gallardo, 2022
 */

package inform.graphics.draw2D


class Polygon(xs: Seq[Double], ys: Seq[Double]) extends java.awt.geom.Path2D.Double:
  if xs.length != ys.length then
    throw new IllegalArgumentException("Polygon: both arguments should have same length")
  if xs.length < 1 then
    throw new IllegalArgumentException("Polygon: both arguments should be non-empty")

  this.moveTo(xs(0), ys(0))
  for i <- 1 until xs.length do
    this.lineTo(xs(i), ys(i))
  this.closePath()

  def coordinates: Seq[(Double, Double)] =
    xs.zip(ys)
    
  def points: Seq[Point] =
    coordinates.map{case (x, y) => Point(x, y)}

  def this(xs: Iterable[Double], ys: Iterable[Double]) =
    this(xs.toSeq, ys.toSeq)
    
  def this(t: (Iterable[Double], Iterable[Double])) =
    this(t._1, t._2)  
    
  def this(xys: Iterable[(Double, Double)]) =
    this (xys.unzip)

  def this(xys: (Double, Double)*) =
    this(xys.toSeq)

  override def toString: String =
    points.mkString("Polygon(", ", ", ")")


object Polygon:
  def apply(xs: Seq[Double], ys: Seq[Double]) =
    new Polygon(xs, ys)

  def apply(xys: Iterable[(Double, Double)]) =
    new Polygon(xys)

  def apply(xys: (Double, Double)*) =
    new Polygon(xys)

  def unapply(p: Polygon): Seq[(Double, Double)] =
    (p.points.map(p => (p.x, p.y)))

  object Points:
    def apply(xys: Iterable[Point]) =
      new Polygon(xys.map(_.x), xys.map(_.y))

    def apply(xys: Point*) =
      new Polygon(xys.map(_.x), xys.map(_.y))

    def unapply(polygon: Polygon): Seq[Point] =
      (polygon.points)

