/*
  Pepe Gallardo, 2023
 */

package classes.point2D.option1

class Point2D:
  var x: Double = 0
  var y: Double = 0

  def moveBy(xOffset: Double, yOffset: Double): Unit =
    x += xOffset
    y += yOffset

  override def toString: String =
    s"Point2D($x, $y)"

@main def point2DTest(): Unit =
  val p1 = Point2D()
  p1.x = 5
  p1.y = 10
  p1.moveBy(100, 200)

  val p2 = Point2D()
  p2.x = p1.y

  println(p1)
  println(p2)
