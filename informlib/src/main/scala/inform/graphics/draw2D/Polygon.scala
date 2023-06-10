/*
  Pepe Gallardo, 2023
 */

package inform.graphics.draw2D

/** A polygon is a closed path defined by a sequence of points.
  *
  * @param xs
  *   x-coordinates of points.
  * @param ys
  *   y-coordinates of points.
  */
class Polygon(xs: Seq[Double], ys: Seq[Double]) extends java.awt.geom.Path2D.Double:
  if xs.length != ys.length then
    throw new IllegalArgumentException("Polygon: both arguments should have same length")
  if xs.length < 1 then
    throw new IllegalArgumentException("Polygon: both arguments should be non-empty")

  this.moveTo(xs(0), ys(0))
  for i <- 1 until xs.length do this.lineTo(xs(i), ys(i))
  this.closePath()

  /** Returns the coordinates of the points defining this polygon.
    *
    * @return
    *   coordinates of the points defining this polygon.
    */
  def coordinates: Seq[(Double, Double)] =
    xs.zip(ys)

  /** Returns the points defining this polygon.
    *
    * @return
    *   points defining this polygon.
    */
  def points: Seq[Point] =
    coordinates.map { case (x, y) => Point(x, y) }

  /** Constructs a polygon from two `Iterables` with X and Y coordinates of points.
    *
    * @param xs
    *   X coordinates of points.
    * @param ys
    *   Y coordinates of points.
    */
  def this(xs: Iterable[Double], ys: Iterable[Double]) =
    this(xs.toSeq, ys.toSeq)

  /** Constructs a polygon from a pair of `Iterable` with X and Y coordinates of points.
    *
    * @param t
    *   a pair of `Iterable` with X and Y coordinates of points.
    */
  def this(t: (Iterable[Double], Iterable[Double])) =
    this(t._1, t._2)

  /** Constructs a polygon from an `Iterable` of coordinates.
    *
    * @param xys
    */
  def this(xys: Iterable[(Double, Double)]) =
    this(xys.unzip)

  /** Constructs a polygon from a sequence of coordinates.
    *
    * @param xys
    *   sequence of coordinates.
    */
  def this(xys: (Double, Double)*) =
    this(xys.toSeq)

  override def toString: String =
    points.mkString("Polygon(", ", ", ")")

object Polygon:
  /** Constructs a polygon from two `Seq`s with X and Y coordinates of points.
    *
    * @param xs
    *   X coordinates of points.
    * @param ys
    *   Y coordinates of points.
    * @return
    *   a polygon.
    */
  def apply(xs: Seq[Double], ys: Seq[Double]) =
    new Polygon(xs, ys)

  /** Constructs a polygon from an `Iterable` of coordinates of points.
    *
    * @param xys
    *   an `Iterable` of coordinates of points.
    * @return
    *   a polygon.
    */
  def apply(xys: Iterable[(Double, Double)]) =
    new Polygon(xys)

  /** Constructs a polygon from a sequence of coordinates of points.
    * @param xys
    *   sequence of coordinates of points.
    *
    * @return
    */
  def apply(xys: (Double, Double)*) =
    new Polygon(xys)

  def unapply(p: Polygon): Seq[(Double, Double)] =
    (p.points.map(p => (p.x, p.y)))

  object Points:
    /** Constructs a polygon from an `Iterable` of points.
      *
      * @param xys
      *   an `Iterable` of points.
      * @return
      *   a polygon.
      */
    def apply(xys: Iterable[Point]) =
      new Polygon(xys.map(_.x), xys.map(_.y))

    /** Constructs a polygon from a sequence of points.
      * @param xys
      *   sequence of points.
      * @return
      *   a polygon.
      */
    def apply(xys: Point*) =
      new Polygon(xys.map(_.x), xys.map(_.y))

    def unapply(polygon: Polygon): Seq[Point] =
      (polygon.points)
