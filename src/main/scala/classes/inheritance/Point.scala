/*
  Pepe Gallardo, 2022
 */

package classes.inheritance

import inform.graphics.color.*

class Point(x0: Double, y0: Double):
  protected var x: Double = x0
  protected var y: Double = y0

  def moveBy(dx: Double, dy: Double): Unit =
    x += dx
    y += dy

  override def toString: String =
    s"Point($x, $y)"


class ColoredPoint(x0: Double, y0: Double, c0: Color)
  extends Point(x0,y0):

  protected var c: Color = c0

  def fade(): Unit =
    c = new Color(c.getRed/2, c.getGreen/2, c.getBlue/2)

  override def toString: String =
    s"ColoredPoint($x, $y, $c)"


@main def pointTest(): Unit =
  var p1: Point = new Point(1, 2)
  var p2: ColoredPoint = new ColoredPoint(3, 4, Color.red)
  var p3: Point = new ColoredPoint(5, 6, Color.blue)
  // Compilation error: var p4: ColoredPoint = new Point(7, 8)

  println(p1.toString)
  println(p2.toString)
  println(p3.toString)

  p2.fade()
  println(p2)

  val points: List[Point] = List(p1, p2, p3)
  println(points)

  for(p <- points)
    p.moveBy(100,100)
  println(points)