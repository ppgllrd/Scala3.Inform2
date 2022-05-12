/*
  Pepe Gallardo, 2022
 */

package photos

import inform.fotos.*
import inform.graphics.color.*

object Photos:
  val red: Color = Color(255,0,0)
  val green: Color = Color(0,255,0)
  val blue: Color = Color(0,0,255)

  val darkRed: Color = Color(128,0,0)

  val purple: Color = Color(255,0,255)

  val black: Color = Color(0,0,0)
  val white: Color = Color(255,255,255)
  val grays: Array[Color] = (for(x <- 0 to 255 by 32) yield new Color(x,x,x)).toArray

  val r: Int = red.getRed
  val g: Int = red.getGreen
  val b: Int = red.getBlue

  def luminescence(color: Color): Double =
    val Color(r, g, b, a) = color
    0.299*r + 0.587*g + 0.114*b

  def toBlackWhite(color: Color): Color =
    val l = luminescence(color).toInt
    Color(l,l,l)

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
    val color = Color(v, v, v)
    for c <- 0 until width do
      photo(r)(c) = color

  photo.actualizar()


@main def photoTest2(): Unit =
  import inform.util.aleatorios.*

  val height = 300
  val width = 200
  val photo = Foto(height,width)

  val alt = Aleatorio(0)

  for r <- 0 until height do
    val red = alt.entero(0, 255)
    val green = alt.entero(0, 255)
    val blue = alt.entero(0, 255)
    val color = Color(red, green, blue)

    for c <- 0 until width do
      photo(r)(c) = color

  photo.actualizar()


@main def photoTest3(): Unit =
  val photo = Foto("data/photos/mandrill.jpg")
  Photos.toBlackWhite(photo)


