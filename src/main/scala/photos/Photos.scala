/*
  Pepe Gallardo, 2022
 */

package photos

import inform.fotos.*
import inform.graphics.color.*

object Photos:
  val red: Color = Color.RGB(255, 0, 0)
  val green: Color = Color.RGB(0, 255, 0)
  val blue: Color = Color.RGB(0, 0, 255)

  val darkRed: Color = Color.RGB(128, 0, 0)

  val purple: Color = Color.RGB(255,0,255)

  val black: Color = Color.RGB(0,0,0)
  val white: Color = Color.RGB(255,255,255)
  val grays: Array[Color] = (for(x <- 0 to 255 by 32) yield Color.RGB(x, x, x)).toArray
  
  val r: Int = red.getRed
  val g: Int = red.getGreen
  val b: Int = red.getBlue

  def luminescence(color: Color): Double =
    val Color(r, g, b) = color
    0.299*r + 0.587*g + 0.114*b

  def toBlackWhite(color: Color): Color =
    val l = luminescence(color).toInt
    Color.RGB(l, l, l)

  def toBlackWhite(photo: Foto): Foto =
    val height = photo.alto
    val width = photo.ancho
    val blackWhitePhoto = Foto(height, width)

    for r <- 0 until height do
      for c <- 0 until width do
        val gray = toBlackWhite(photo(r)(c))
        blackWhitePhoto(r)(c) = gray

    blackWhitePhoto.actualizar()
    blackWhitePhoto


@main def photoTest1(): Unit =
  val height = 300
  val width = 200
  val photo = Foto(height, width)

  for r <- 0 until height do
    val v = r * 255 / height
    val color = Color.RGB(v, v, v)
    for c <- 0 until width do
      photo(r)(c) = color

  photo.actualizar()


@main def photoTest2(): Unit =
  import inform.util.random.*

  val height = 300
  val width = 200
  val photo = Foto(height,width)

  val rnd = Random(0)

  for r <- 0 until height do
    val red = rnd.uniform(256)
    val green = rnd.uniform(256)
    val blue = rnd.uniform(256)
    val color = Color.RGB(red, green, blue)

    for c <- 0 until width do
      photo(r)(c) = color

  photo.actualizar()


@main def photoTest3(): Unit =
  val photo = Foto("data/photos/mandrill.jpg")
  Photos.toBlackWhite(photo)


