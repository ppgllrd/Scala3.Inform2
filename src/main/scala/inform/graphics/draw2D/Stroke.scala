/*
  Pepe Gallardo, 2022
 */

package inform.graphics.draw2D


class Stroke( width: Double = 1, cap: Int = Stroke.CAP_SQUARE, join: Int = Stroke.JOIN_MITER
            , miterlimit: Double = 10, dash: Array[Double] = null, dashPhase: Double = 0
            )
  extends java.awt.BasicStroke(width.toFloat, cap, join, miterlimit.toFloat
    , if (dash == null) null else dash.map(_.toFloat)
    , dashPhase.toFloat
    ):

  def this(width: Int) =
    this(width, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 10.0, null, 0.0)

  override def toString: String = s"Stroke($width, $cap, $join, $miterlimit, ${dash.mkString("Array(", ",", ")")}, $dashPhase)"


object Stroke:
  val CAP_BUTT: Int = java.awt.BasicStroke.CAP_BUTT
  val CAP_ROUND: Int = java.awt.BasicStroke.CAP_ROUND
  val CAP_SQUARE: Int = java.awt.BasicStroke.CAP_SQUARE

  val JOIN_MITER: Int = java.awt.BasicStroke.JOIN_MITER
  val JOIN_ROUND: Int = java.awt.BasicStroke.JOIN_ROUND
  val JOIN_BEVEL: Int = java.awt.BasicStroke.JOIN_BEVEL

  def apply(width: Double = 1, cap: Int = CAP_SQUARE, join: Int = JOIN_MITER, miterlimit: Double = 10, dash: Array[Double] = null, dashPhase: Double = 0) =
    new Stroke(width, cap, join, miterlimit, dash, dashPhase)

  /**
   * Constructs a drawing Stroke.
   * @param width Width of the Stroke.
   */
  def apply(width: Double) =
    new Stroke(width)

