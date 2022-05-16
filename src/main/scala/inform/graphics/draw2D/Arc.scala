/*
  Pepe Gallardo, 2022
 */

package inform.graphics.draw2D


class Arc( val left: Double, val top: Double, width: Double, height: Double
         , val alpha: Radian, val beta: Radian, val kind: Int = Arc.OPEN
         )
  extends java.awt.geom.Arc2D.Double(left, top, width, height, Arc.radian2Degrees(alpha), Arc.radian2Degrees(beta), kind):

  override def toString: String = 
    s"Arc($left, $top, $width, $height, $alpha, $beta, $kind)"


object Arc: 
  val OPEN: Int = java.awt.geom.Arc2D.OPEN
  val CHORD: Int = java.awt.geom.Arc2D.CHORD
  val PIE: Int = java.awt.geom.Arc2D.PIE

  def apply(left: Double, top: Double, width: Double, height: Double, alpha: Radian, beta: Radian, kind: Int = Arc.OPEN): Arc =
    new Arc(left, top, width, height, alpha, beta, kind)

  def unapply(a: Arc): Option[(Double, Double, Double, Double, Radian, Radian, Int)] =
    Some(a.left, a.top, a.width, a.height, a.alpha, a.beta, a.kind)

  def apply(leftTop: Point, width: Double, height: Double, alpha: Radian, beta: Radian, kind: Int): Arc =
    new Arc(leftTop.x, leftTop.y, width, height, alpha, beta, kind)

  def apply(rect: Rectangle, alpha: Radian, beta: Radian, kind: Int): Arc = rect match 
    case Rectangle(left, top, width, height) => new Arc(left, top, width, height, alpha, beta, kind)
 
  private def radian2Degrees(x: Radian) = 180 * x / scala.math.Pi






