/*
  Pepe Gallardo, 2023
 */

package classes.inheritance

import inform.graphics.color.*

class Point(x0: Double, y0: Double):
  protected var x: Double = x0
  protected var y: Double = y0

  def moveBy(xOffset: Double, yOffset: Double): Unit =
    x += xOffset
    y += yOffset

  override def toString: String =
    s"Point($x, $y)"

class ColoredPoint(x0: Double, y0: Double, c0: Color) extends Point(x0, y0):

  protected var c: Color = c0

  def fade(): Unit =
    c = Color(c.getRed / 2, c.getGreen / 2, c.getBlue / 2)

  override def toString: String =
    s"ColoredPoint($x, $y, $c)"

@main def pointTest(): Unit =
  val p1: Point = Point(1, 2)
  val p2: ColoredPoint = ColoredPoint(3, 4, Color.red)
  val p3: Point = ColoredPoint(5, 6, Color.blue)
  // Compilation error: val p4: ColoredPoint = new Point(7, 8)

  println(p1) // uses toString
  println(p2)
  println(p3)

  p2.fade()
  println(p2)

  val points: List[Point] = List(p1, p2, p3)
  println(points)

  for p <- points do p.moveBy(100, 100)
  println(points)
