/*
  Pepe Gallardo, 2023
 */

package inform.graphics.draw2D

/** A point in the plane.
  *
  * @param _x
  *   X coordinate.
  * @param _y
  *   Y coordinate.
  */
class Point(_x: Double, _y: Double) extends java.awt.geom.Point2D.Double(_x, _y):
  override def toString: String = s"Point($x, $y)"

object Point:
  /** Constructs a Point from its X and Y coordinates.
    * @param x
    *   X coordinate.
    * @param y
    *   Y coordinate.
    */
  def apply(x: Double, y: Double): Point =
    new Point(x, y)

  def unapply(point: Point): (Double, Double) =
    (point.x, point.y)
