/*
  Pepe Gallardo, 2023
 */

package inform.graphics.draw2D

/** An ellipse.
  *
  * @param _left
  *   X coordinate of the upper-left corner of the framing rectangle.
  * @param _top
  *   Y coordinate of the upper-left corner of the framing rectangle.
  * @param _width
  *   width of the framing rectangle.
  * @param _height
  *   height of the framing rectangle.
  */
class Ellipse(_left: Double, _top: Double, _width: Double, _height: Double)
    extends java.awt.geom.Ellipse2D.Double(_left, _top, _width, _height):
  val left: Double = x
  val top: Double = y
  override def toString: String = s"Ellipse($left, $top, $width, $height)"

object Ellipse:
  /** Constructs an Ellipse from the specified coordinates.
    * @param left
    *   X coordinate of the upper-left corner of the framing rectangle.
    * @param top
    *   Y coordinate of the upper-left corner of the framing rectangle.
    * @param width
    *   width of the framing rectangle.
    * @param height
    *   height of the framing rectangle.
    */
  def apply(left: Double, top: Double, width: Double, height: Double): Ellipse =
    new Ellipse(left, top, width, height)

  /** Constructs an Ellipse from the specified coordinates.
    * @param leftTop
    *   X and Y coordinates of the upper-left corner of the framing rectangle.
    * @param width
    *   width of the framing rectangle.
    * @param height
    *   height of the framing rectangle.
    */
  def apply(leftTop: Point, width: Double, height: Double): Ellipse =
    new Ellipse(leftTop.x, leftTop.y, width, height)

  /** Constructs an Ellipse from the specified framing Rectangle.
    * @param rectangle
    *   framing Rectangle.
    */
  def fromRectangle(rectangle: Rectangle): Ellipse =
    new Ellipse(rectangle.left, rectangle.top, rectangle.width, rectangle.height)

  def unapply(ellipse: Ellipse): (Double, Double, Double, Double) =
    (ellipse.left, ellipse.top, ellipse.width, ellipse.height)
