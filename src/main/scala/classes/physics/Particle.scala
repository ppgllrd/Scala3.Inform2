/*
  Pepe Gallardo, 2022
 */

package classes.physics

import inform.graphics.color.Color
import inform.graphics.draw2D.*

import scala.math.*

class Particle(private var pos: Vector2D
              , private var vel: Vector2D
              , val mass: Double
              , val radius: Double
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
    def circle(x: Double, y: Double, r: Double, c: Color, c2: Color): Unit =
      val r2 = r / 2
      g2D.setColor(c)
      g2D.fill(Ellipse(x - r2, y - r2, r, r))

      g2D.setStroke(Stroke(5e8f))
      g2D.setColor(c2)
      g2D.draw(Ellipse(x - r2, y - r2, r, r))

    circle(pos.x, pos.y, radius, Color.green, Color.blue)

