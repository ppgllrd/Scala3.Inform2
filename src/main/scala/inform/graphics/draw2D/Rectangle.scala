/*
  Pepe Gallardo, 2022
 */

package inform.graphics.draw2D


class Rectangle(_left: Double, _top: Double, _width: Double, _height: Double) extends java.awt.geom.Rectangle2D.Double(_left, _top, _width, _height):
  val left: Double = x
  val top: Double = y
  override def toString: String = s"Rectangle($left, $top, $width, $height)"

  def apply(): Transform = new Transform()


object Rectangle:
  /**
   * Constructs a Rectangle from the specified coordinates.
   * @param left X coordinate of the upper-left corner of the rectangle.
   * @param top Y coordinate of the upper-left corner of the rectangle.
   * @param width width of the rectangle.
   * @param height height of the rectangle
   */
  def apply(left: Double, top: Double, width: Double, height: Double): Rectangle =
    new Rectangle(left, top, width, height)

  
  /**
   * Constructs a Rectangle from the specified coordinates.
   * @param leftTop X and Y coordinates of the upper-left corner of the rectangle.
   * @param width width of the rectangle.
   * @param height height of the rectangle
   */
  def apply(leftTop: Point, width: Double, height: Double): Rectangle =
    new Rectangle(leftTop.x, leftTop.y, width, height)

  
  /**
   * Constructs the framing rectangle for the specified Ellipse.
   * @param ellipse Ellipse to frame.
   */
  def fromEllipse(ellipse: Ellipse): Rectangle =
    Rectangle(ellipse.left, ellipse.top, ellipse.width, ellipse.height)

  
  def unapply(rectangle: Rectangle): (Double, Double, Double, Double) =
    (rectangle.left, rectangle.top, rectangle.width, rectangle.height)
    
    