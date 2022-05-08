/*
  Pepe Gallardo, 2022
 */

package classes.point2D.option4

object Point2D:
  private var counter: Int = 0

  def numberOfPoints: Int = counter


class Point2D(private var x: Double, private var y: Double):
  Point2D.counter += 1

  def this() =
    this(0, 0)

  def this(v: Double) =
    this(v, v)

  def abscissa: Double = x

  def ordinate: Double = y

  def moveTo(x1: Double, y1: Double): Unit =
    x = x1
    y = y1

  def moveBy(xOffset: Double, yOffset: Double): Unit =
    moveTo(abscissa + xOffset, ordinate + yOffset)

  override def toString: String =
    s"Point2D($abscissa, $ordinate)"

  override def equals(obj: Any): Boolean = obj match
    case that: Point2D => abscissa == that.abscissa && ordinate == that.ordinate
    case _ => false

  def apply(index: Int): Double =
    if index == 0 then
      abscissa
    else if index == 1 then
      ordinate
    else
      sys.error("Invalid index")

  def update(index: Int, value: Double): Unit =
    if index == 0 then
      x = value
    else if index == 1 then
      y = value
    else
      sys.error("Invalid index")


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

  println(s"The total number of points that were created is ${Point2D.numberOfPoints}")