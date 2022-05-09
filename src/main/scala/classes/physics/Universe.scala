/*
  Pepe Gallardo, 2022
 */

package classes.physics

import inform.graphics.draw2D.{Graphics2D, GraphicsWindow}


class Universe(private val particles: Array[Particle]
               , val radius: Double
              ):

  def advance(dt: Double): Unit =
    val forces = Array.fill(particles.length)(Vector2D(0, 0))

    for i <- 0 until forces.length do
      for j <- 0 until forces.length do
        if (i != j)
          forces(i) += particles(i).forceFrom(particles(j))

    for i <- 0 until particles.length do
      particles(i).moveBy(forces(i), dt)


  def drawOn(g2D: Graphics2D): Unit =
    for (p <- particles)
      p.drawOn(g2D)


@main def universe1Test(): Unit =
  val particles = Array(
      Particle(Vector2D(0, 4.5e10), Vector2D(1e4, 0), 1.5e30, 8e9)
    , Particle(Vector2D(0, -4.5e10), Vector2D(-1e4, 0), 1.5e30, 8e9)
  )

  val univ = Universe(particles, 6e10)

  val v = GraphicsWindow("Physics", 600, 600)
  v.scale = 1000 / (univ.radius * 2)

  val dt = 10000
  while (true) {
    univ.advance(dt)
    v.drawWith(univ.drawOn)
    Thread.sleep(5)
  }
