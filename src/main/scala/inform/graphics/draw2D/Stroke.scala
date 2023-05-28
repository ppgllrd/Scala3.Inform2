/*
  Pepe Gallardo, 2023
 */

package inform.graphics.draw2D

class Stroke(
    width: Double = 1,
    cap: Int = Stroke.CAP_SQUARE,
    join: Int = Stroke.JOIN_MITER,
    miterlimit: Double = 10,
    dash: Array[Double] = null,
    dashPhase: Double = 0
) extends java.awt.BasicStroke(
      width.toFloat,
      cap,
      join,
      miterlimit.toFloat,
      if (dash == null) null else dash.map(_.toFloat),
      dashPhase.toFloat
    ):

  def this(width: Int) =
    this(width, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 10.0, null, 0.0)

  override def toString: String =
    s"Stroke($width, $cap, $join, $miterlimit, ${dash.mkString("Array(", ",", ")")}, $dashPhase)"

object Stroke:
  val CAP_BUTT: Int = java.awt.BasicStroke.CAP_BUTT
  val CAP_ROUND: Int = java.awt.BasicStroke.CAP_ROUND
  val CAP_SQUARE: Int = java.awt.BasicStroke.CAP_SQUARE

  val JOIN_MITER: Int = java.awt.BasicStroke.JOIN_MITER
  val JOIN_ROUND: Int = java.awt.BasicStroke.JOIN_ROUND
  val JOIN_BEVEL: Int = java.awt.BasicStroke.JOIN_BEVEL

  /** Constructs a drawing Stroke.
    * @param width
    *   width of the Stroke.
    * @param cap
    *   the decoration of the ends of a BasicStroke.
    * @param join
    *   the decoration applied where path segments meet.
    * @param miterlimit
    *   the limit to trim the miter join. The miterlimit must be greater than or equal to 1.0.
    * @param dash
    *   the array representing the dashing pattern.
    * @param dashPhase
    *   the offset to start the dashing pattern
    * @return
    */
  def apply(
      width: Double = 1,
      cap: Int = CAP_SQUARE,
      join: Int = JOIN_MITER,
      miterlimit: Double = 10,
      dash: Array[Double] = null,
      dashPhase: Double = 0
  ) =
    new Stroke(width, cap, join, miterlimit, dash, dashPhase)

  /** Constructs a drawing Stroke.
    * @param width
    *   width of the Stroke.
    */
  def apply(width: Double) =
    new Stroke(width)

  def unapply(s: Stroke): (Double, Int, Int, Double, Array[Double], Double) =
    (
      s.getLineWidth.toDouble,
      s.getEndCap,
      s.getLineJoin,
      s.getMiterLimit,
      s.getDashArray.map(_.toDouble),
      s.getDashPhase.toDouble
    )
