/*
  Pepe Gallardo, 2022
 */

package classes.physics

import inform.graphics.draw2D.{Graphics2D, GraphicsWindow}


class Universe( private val particles: Array[Particle]
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

  val w = GraphicsWindow("Physics", 600, 600)
  w.scale = 1000 / (univ.radius * 2)

  val dt = 10000
  while true do
    univ.advance(dt)
    w.drawWith(univ.drawOn)
    Thread.sleep(5)


@main def universe2Test(): Unit =
  val particles = Array(
      Particle(Vector2D(0, 0), Vector2D(0.05e4, 0), 6.97e24, 5e9)
    , Particle(Vector2D(0, 4.5e10), Vector2D(3e4, 0), 2.999e30, 1e10)
    , Particle(Vector2D(0, -4.5e10), Vector2D(-3e4, 0), 2.999e30, 1e10)
  )

  val univ = Universe(particles, 7e10)

  val w = GraphicsWindow("Physics", 600, 600)
  w.scale = 1000 / (univ.radius * 2)

  val dt = 3600
  while true do
    univ.advance(dt)
    w.drawWith(univ.drawOn)
    Thread.sleep(5)


@main def universe3Test(): Unit =
  val particles = Array(
      Particle(Vector2D(-3.5e10, 0), Vector2D(0, 1.4e3), 3e28, 5e9)
    , Particle(Vector2D(-1e10, 0), Vector2D(0, 1.64e4), 3e28, 5e9)
    , Particle(Vector2D(1e10, 0), Vector2D(0, -1.64e4), 3e28, 5e9)
    , Particle(Vector2D(3.5e10, 0), Vector2D(0, -1.4e3), 3e28, 5e9)
  )

  val univ = Universe(particles, 1e11)

  val w = GraphicsWindow("Physics", 600, 600)
  w.scale = 1000 / (univ.radius * 2)

  val dt = 20000
  while true do
    univ.advance(dt)
    w.drawWith(univ.drawOn)
    Thread.sleep(4)


@main def universe4Test(): Unit =
  val particles = Array(
      Particle(Vector2D(-3.5e10, 0), Vector2D(0, 1.4e3), 3e28, 5e9)
    , Particle(Vector2D(-1e10, 0), Vector2D(0, 1.645e4), 3e28, 5e9)
    , Particle(Vector2D(1e10, 0), Vector2D(0, -1.645e4), 3e28, 5e9)
    , Particle(Vector2D(3.5e10, 0), Vector2D(0, -1.4e3), 3e28, 5e9)
  )

  val univ = Universe(particles, 1e11)

  val w = GraphicsWindow("Physics", 600, 600)
  w.scale = 1000 / (univ.radius * 2)

  val dt = 20000
  while true do
    univ.advance(dt)
    w.drawWith(univ.drawOn)
    Thread.sleep(4)

