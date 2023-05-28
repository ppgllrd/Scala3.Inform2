/*
  Pepe Gallardo, 2023
 */

package classes.physics

import inform.graphics.color.Color
import inform.graphics.draw2D.*

import scala.math.*

class Particle(
    private var pos: Vector2D,
    private var vel: Vector2D,
    val mass: Double,
    val radius: Double
):

  def moveBy(force: Vector2D, dt: Double): Unit =
    val acc = force * (1 / mass)
    vel += acc * dt
    pos += vel * dt

  def forceFrom(that: Particle): Vector2D =
    val G = 6.67e-11
    val delta = that.pos - this.pos
    val dist = delta.magnitude
    val f = (G * this.mass * that.mass) / pow(dist, 2)
    delta.direction * f

  def drawOn(g2D: Graphics2D): Unit =
    def circle(x: Double, y: Double, r: Double, fillColor: Color, borderColor: Color): Unit =
      val diameter = 2 * r
      g2D.setColor(fillColor)
      g2D.fill(Ellipse(x - r, y - r, diameter, diameter))

      g2D.setStroke(Stroke(8e8f))
      g2D.setColor(borderColor)
      g2D.draw(Ellipse(x - r, y - r, diameter, diameter))

    circle(pos.x, pos.y, radius, Color.green, Color.blue)
