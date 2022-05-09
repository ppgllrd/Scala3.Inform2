/*
  Pepe Gallardo, 2022
 */

package classes.physics

import scala.math.*

class Vector2D(val x: Double, val y: Double):

  def *(z: Double): Vector2D = Vector2D(x * z, y * z)

  def +(that: Vector2D): Vector2D = Vector2D(x + that.x, y + that.y)

  def -(that: Vector2D): Vector2D = Vector2D(x - that.x, y - that.y)

  def magnitude: Double = sqrt(pow(x, 2) + pow(y, 2))

  def direction: Vector2D =
    if magnitude == 0.0 then
      sys.error("Null Vector has no direction")
    else
      this * (1.0 / this.magnitude)

  override def toString: String =
    s"Vector2D($x, $y)"


@main def vectorTest(): Unit =
  val v1 = Vector2D(1, 2)
  val v2 = v1 * 10
  val v3 = v1 + v2

  println(v1)
  println(v1.direction)
  println(v1.direction.magnitude)
  println(v2)
  println(v3)