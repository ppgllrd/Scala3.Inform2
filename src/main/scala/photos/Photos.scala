/*
  Pepe Gallardo, 2023
 */

// Demonstrates photo creation, manipulation, and conversion to grayscale.

package photos

import inform.photo.*
import inform.graphics.color.*

/** Utility object for color constants and photo transformations. */
object Photos:
  val red: Color = Color.RGB(255, 0, 0)
  val green: Color = Color.RGB(0, 255, 0)
  val blue: Color = Color.RGB(0, 0, 255)

  val darkRed: Color = Color.RGB(128, 0, 0)

  val purple: Color = Color.RGB(255, 0, 255)

  val black: Color = Color.RGB(0, 0, 0)
  val white: Color = Color.RGB(255, 255, 255)
  val grays: Array[Color] = (for x <- 0 to 255 by 32 yield Color.RGB(x, x, x)).toArray

  val r: Int = red.getRed
  val g: Int = red.getGreen
  val b: Int = red.getBlue

  /** Computes perceived brightness using the ITU-R BT.601 luma formula. */
  def luminescence(color: Color): Double =
    val Color.RGB(r, g, b) = color
    0.299 * r + 0.587 * g + 0.114 * b

  def toBlackWhite(color: Color): Color =
    val l = luminescence(color).toInt
    Color.RGB(l, l, l)

  def toBlackWhite(photo: Photo): Photo =
    val height = photo.height
    val width = photo.width
    val blackWhitePhoto = Photo.ofDim(height, width)

    for r <- 0 until height do
      for c <- 0 until width do
        val gray = toBlackWhite(photo(r)(c))
        blackWhitePhoto(r)(c) = gray

    blackWhitePhoto

@main def photoTest1(): Unit =
  val height = 350
  val width = 250
  val photo = Photo.ofDim(height, width)

  for r <- 0 until height do
    val v = r * 255 / (height - 1)
    val color = Color.RGB(v, v, v)
    for c <- 0 until width do photo(r)(c) = color

  photo.show()

@main def photoTest2(): Unit =
  import inform.util.random.*

  val height = 300
  val width = 250
  val photo = Photo.ofDim(height, width)

  val rnd = Random(0)

  for r <- 0 until height do
    val red = rnd.uniform(0, 256)
    val green = rnd.uniform(0, 256)
    val blue = rnd.uniform(0, 256)
    val color = Color.RGB(red, green, blue)

    for c <- 0 until width do photo(r)(c) = color

  photo.show()

@main def photoTest3(): Unit =
  val photo = Photo.from("data/photos/mandrill.jpg")
  photo.show()
  val blackWhitePhoto = Photos.toBlackWhite(photo)
  blackWhitePhoto.show()
