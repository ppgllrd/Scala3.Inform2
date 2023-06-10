/*
  Pepe Gallardo, 2023
 */

package inform.graphics.draw2D

/** An arc.
  *
  * @param left
  *   X coordinate of the upper-left corner of the framing rectangle.
  * @param top
  *   Y coordinate of the upper-left corner of the framing rectangle.
  * @param width
  *   width of the framing rectangle.
  * @param height
  *   height of the framing rectangle.
  * @param alpha
  *   starting angle of the arc in radians.
  * @param beta
  *   angular extent of the arc in radians.
  * @param kind
  *   type of arc: Arc.OPEN, Arc.CHORD or Arc.PIE.
  */
class Arc(
    val left: Double,
    val top: Double,
    width: Double,
    height: Double,
    val alpha: Radian,
    val beta: Radian,
    val kind: Int = Arc.OPEN
) extends java.awt.geom.Arc2D.Double(
      left,
      top,
      width,
      height,
      Arc.radian2Degrees(alpha),
      Arc.radian2Degrees(beta),
      kind
    ):

  override def toString: String =
    s"Arc($left, $top, $width, $height, $alpha, $beta, $kind)"

object Arc:
  val OPEN: Int = java.awt.geom.Arc2D.OPEN
  val CHORD: Int = java.awt.geom.Arc2D.CHORD
  val PIE: Int = java.awt.geom.Arc2D.PIE

  /** Constructs an arc from the specified parameters.
    *
    * @param left
    *   X coordinate of the upper-left corner of the framing rectangle.
    * @param top
    *   Y coordinate of the upper-left corner of the framing rectangle.
    * @param width
    *   width of the framing rectangle.
    * @param height
    *   height of the framing rectangle.
    * @param alpha
    *   starting angle of the arc in radians.
    * @param beta
    *   angular extent of the arc in radians.
    * @param kind
    *   type of arc: Arc.OPEN, Arc.CHORD or Arc.PIE.
    * @return
    *   a new arc constructed from specified parameters.
    */
  def apply(
      left: Double,
      top: Double,
      width: Double,
      height: Double,
      alpha: Radian,
      beta: Radian,
      kind: Int = Arc.OPEN
  ): Arc =
    new Arc(left, top, width, height, alpha, beta, kind)

  /** Constructs an arc from the specified parameters.
    *
    * @param leftTop
    *   X and Y coordinates of the upper-left corner of the framing rectangle.
    * @param width
    *   width of the framing rectangle.
    * @param height
    *   height of the framing rectangle.
    * @param alpha
    *   starting angle of the arc in radians.
    * @param beta
    *   angular extent of the arc in rqdians.
    * @param kind
    *   type of arc: OPEN, CHORD or PIE.
    * @return
    *   a new arc constructed from specified parameters.
    */
  def apply(
      leftTop: Point,
      width: Double,
      height: Double,
      alpha: Radian,
      beta: Radian,
      kind: Int
  ): Arc =
    new Arc(leftTop.x, leftTop.y, width, height, alpha, beta, kind)

  /** Constructs an arc from the specified parameters.
    *
    * @param rect
    *   framing rectangle.
    * @param alpha
    *   starting angle of the arc in radians.
    * @param beta
    *   angular extent of the arc in radians.
    * @param kind
    *   type of arc: Arc.OPEN, Arc.CHORD or Arc.PIE.
    * @return
    *   a new arc constructed from specified parameters.
    */
  def fromRectangle(rect: Rectangle, alpha: Radian, beta: Radian, kind: Int): Arc = rect match
    case Rectangle(left, top, width, height) => new Arc(left, top, width, height, alpha, beta, kind)

  def unapply(a: Arc): (Double, Double, Double, Double, Radian, Radian, Int) =
    (a.left, a.top, a.width, a.height, a.alpha, a.beta, a.kind)

  private def radian2Degrees(x: Radian) = 180 * x / scala.math.Pi
