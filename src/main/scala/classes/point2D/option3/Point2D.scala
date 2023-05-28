/*
  Pepe Gallardo, 2023
 */

package classes.point2D.option3

class Point2D(x0: Double, y0: Double):
  private var xs = Array(x0, y0)

  def this() =
    this(0, 0)

  def this(v: Double) =
    this(v, v)

  def abscissa: Double = xs(0)

  def ordinate: Double = xs(1)

  def moveTo(x1: Double, y1: Double): Unit =
    xs(0) = x1
    xs(1) = y1

  def moveBy(xOffset: Double, yOffset: Double): Unit =
    moveTo(abscissa + xOffset, ordinate + yOffset)

  override def toString: String =
    s"Point2D($abscissa, $ordinate)"

  override def equals(obj: Any): Boolean = obj match
    case that: Point2D => abscissa == that.abscissa && ordinate == that.ordinate
    case _             => false

  def apply(index: Int): Double =
    if index == 0 then abscissa
    else if index == 1 then ordinate
    else sys.error("Invalid index")

  def update(index: Int, value: Double): Unit =
    if index == 0 then xs(0) = value
    else if index == 1 then xs(1) = value
    else sys.error("Invalid index")

@main def point2DTest(): Unit =
  val p1 = Point2D(5, 10)
  p1.moveBy(100, 200)

  val p2 = Point2D(p1.abscissa, 300)
  println(p1)
  println(p2)
  println(p1 == p2)

  p1(0) = 3
  p1(1) = 7

  println(p1)

  val h = p1(0)
  println(h)

  val p3 = Point2D()
  println(p3)

  val p4 = Point2D(10)
  println(p4)
