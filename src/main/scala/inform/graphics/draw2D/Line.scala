/*
  Pepe Gallardo, 2022
 */

package inform.graphics.draw2D

class Line(_x1: Double, _y1: Double, _x2: Double, _y2: Double) extends java.awt.geom.Line2D.Double(_x1, _y1, _x2, _y2):
  override def toString: String = s"Line($x1, $y1, $x2, $y2)"


object Line:
  /**
   * Constructs a Line between to Points.
   * @param x1 X coordinate of first point.
   * @param y1 Y coordinate of first point.
   * @param x2 X coordinate of second point.
   * @param y2 Y coordinate of second point.
   */
  def apply(x1: Double, y1: Double, x2: Double, y2: Double): Line =
    new Line(x1, y1, x2, y2)
    
    
  /**
   * Constructs a Line between two Points.
   * @param p1 X and Y coordinates of first point.
   * @param p2 X and Y coordinates of second point.
   */
  def apply(p1: (Double, Double), p2: (Double, Double)): Line =
    new Line(p1._1, p1._2, p2._1, p2._2)

  
  /**
   * Constructs a Line between two Points.
   * @param p1 First point.
   * @param p2 Second point.
   */
  def apply(p1: Point, p2: Point): Line =
    new Line(p1.x, p1.y, p2.x, p2.y)

  
  def unapply(l: Line): Option[(Double, Double, Double, Double)] =
    Some(l.getX1, l.getY1, l.getX2, l.getY2)
