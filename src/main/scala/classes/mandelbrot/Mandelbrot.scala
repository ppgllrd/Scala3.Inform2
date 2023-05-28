/*
  Pepe Gallardo, 2023
 */

package classes.mandelbrot

object Mandelbrot:
  import inform.photo.*

  val maxIters = 255

  // Returns iteration in which we firstly found a modulus larger than 2
  // o maxIters otherwise
  def inMandelbrot(c: Complex): Int =
    var z: Complex = c
    var bounded = true
    var i = 0
    while bounded && i < maxIters do
      if z.mod > 2.0 then bounded = false // gets out of loop to return i
      else
        z = z * z + c
        i += 1
    i // i will be maxIters if all modulus were <= 2.0

  val pixels = 512 // photo will consist of 512x512 pixels

  def drawMandelbrot(real: Double, imaginary: Double, diameter: Double): Unit =
    val radius = diameter / 2
    val photo = Photo(pixels, pixels)
    for i <- 0 until pixels do
      for j <- 0 until pixels do
        // Compute point in complex plane corresponding to pixel with coordinates (i,j)
        val re = real - radius + diameter * j / (pixels - 1)
        val im = imaginary - radius + diameter * i / (pixels - 1)
        val c = Cartesian(re, im)
        // Get its color
        val iters = inMandelbrot(c)
        photo(i)(j) = inform.graphics.color.palette.fire(iters)
      photo.show()
    photo.show()

  @main def mandelbrotTest(): Unit =
    drawMandelbrot(-0.5, 0, 2)
