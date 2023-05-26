/******************************************************************************
 * Informática. Grado en Matemáticas. Universidad de Málaga
 * @ Pepe Gallardo
 *
 * Palettes with colors
 *
 *****************************************************************************/

package inform.graphics.color.palette

import inform.graphics.color.*

private val maxColors: Int = 256

lazy val gray: Array[Color] = {
  val cs = new Array[Color](maxColors)
  for (i <- 0 until maxColors) {
    val c = maxColors - (i + 1)
    gray(i) = new Color (c, c, c)
  }
  cs
}

lazy val fire: Array[Color] =
  Array( new Color(0, 0, 0), new Color(60, 60, 60), new Color(64, 60, 60)
    , new Color(72, 60, 60), new Color(76, 56, 56), new Color(84, 56, 56)
    , new Color(88, 52, 52), new Color(96, 52, 52), new Color(100, 48, 48)
    , new Color(108, 48, 48), new Color(112, 44, 44), new Color(120, 44, 44)
    , new Color(128, 40, 40), new Color(132, 40, 40), new Color(140, 36, 36)
    , new Color(144, 36, 36), new Color(152, 32, 32), new Color(156, 32, 32)
    , new Color(164, 28, 28), new Color(168, 28, 28), new Color(176, 24, 24)
    , new Color(180, 24, 24), new Color(188, 20, 20), new Color(196, 20, 20)
    , new Color(200, 16, 16), new Color(208, 16, 16), new Color(212, 12, 12)
    , new Color(220, 12, 12), new Color(224, 8, 8), new Color(232, 8, 8)
    , new Color(236, 4, 4), new Color(244, 4, 4), new Color(252, 0, 0)
    , new Color(252, 4, 0), new Color(252, 12, 0), new Color(252, 20, 0)
    , new Color(252, 28, 0), new Color(252, 36, 0), new Color(252, 44, 0)
    , new Color(252, 52, 0), new Color(252, 60, 0), new Color(252, 68, 0)
    , new Color(252, 76, 0), new Color(252, 84, 0), new Color(252, 92, 0)
    , new Color(252, 100, 0), new Color(252, 108, 0), new Color(252, 116, 0)
    , new Color(252, 124, 0), new Color(252, 132, 0), new Color(252, 140, 0)
    , new Color(252, 148, 0), new Color(252, 156, 0), new Color(252, 164, 0)
    , new Color(252, 172, 0), new Color(252, 180, 0), new Color(252, 188, 0)
    , new Color(252, 196, 0), new Color(252, 204, 0), new Color(252, 212, 0)
    , new Color(252, 220, 0), new Color(252, 228, 0), new Color(252, 236, 0)
    , new Color(252, 244, 0), new Color(252, 252, 0), new Color(252, 252, 4)
    , new Color(252, 252, 12), new Color(252, 252, 20), new Color(252, 252, 28)
    , new Color(252, 252, 36), new Color(252, 252, 44), new Color(252, 252, 52)
    , new Color(252, 252, 60), new Color(252, 252, 68), new Color(252, 252, 76)
    , new Color(252, 252, 84), new Color(252, 252, 92), new Color(252, 252, 100)
    , new Color(252, 252, 108), new Color(252, 252, 116), new Color(252, 252, 124)
    , new Color(252, 252, 132), new Color(252, 252, 140), new Color(252, 252, 148)
    , new Color(252, 252, 156), new Color(252, 252, 164), new Color(252, 252, 172)
    , new Color(252, 252, 180), new Color(252, 252, 188), new Color(252, 252, 196)
    , new Color(252, 252, 204), new Color(252, 252, 212), new Color(252, 252, 220)
    , new Color(252, 252, 228), new Color(252, 252, 236), new Color(252, 252, 244)
    , new Color(252, 252, 252), new Color(252, 252, 252), new Color(252, 248, 248)
    , new Color(252, 248, 244), new Color(252, 244, 240), new Color(252, 244, 236)
    , new Color(252, 240, 232), new Color(252, 240, 228), new Color(252, 236, 224)
    , new Color(252, 236, 220), new Color(252, 232, 216), new Color(252, 232, 212)
    , new Color(252, 228, 208), new Color(252, 228, 204), new Color(252, 224, 200)
    , new Color(252, 224, 196), new Color(252, 220, 192), new Color(252, 220, 188)
    , new Color(252, 216, 184), new Color(252, 216, 180), new Color(252, 212, 176)
    , new Color(252, 212, 172), new Color(252, 208, 168), new Color(252, 208, 164)
    , new Color(252, 204, 160), new Color(252, 204, 156), new Color(252, 200, 152)
    , new Color(252, 200, 148), new Color(252, 196, 144), new Color(252, 196, 140)
    , new Color(252, 192, 136), new Color(252, 192, 132), new Color(252, 188, 128)
    , new Color(252, 184, 124), new Color(252, 184, 120), new Color(252, 180, 116)
    , new Color(252, 180, 112), new Color(252, 176, 108), new Color(252, 176, 104)
    , new Color(252, 172, 100), new Color(252, 172, 96), new Color(252, 168, 92)
    , new Color(252, 168, 88), new Color(252, 164, 84), new Color(252, 164, 80)
    , new Color(252, 160, 76), new Color(252, 160, 72), new Color(252, 156, 68)
    , new Color(252, 156, 64), new Color(252, 152, 60), new Color(252, 152, 56)
    , new Color(252, 148, 52), new Color(252, 148, 48), new Color(252, 144, 44)
    , new Color(252, 144, 40), new Color(252, 140, 36), new Color(252, 140, 32)
    , new Color(252, 136, 28), new Color(252, 136, 24), new Color(252, 132, 20)
    , new Color(252, 132, 16), new Color(252, 128, 12), new Color(252, 128, 8)
    , new Color(252, 124, 4), new Color(252, 120, 0), new Color(252, 120, 0)
    , new Color(252, 116, 0), new Color(252, 112, 0), new Color(252, 108, 0)
    , new Color(252, 104, 0), new Color(252, 100, 0), new Color(252, 96, 0)
    , new Color(252, 92, 0), new Color(252, 88, 0), new Color(252, 84, 0)
    , new Color(252, 80, 0), new Color(252, 76, 0), new Color(252, 72, 0)
    , new Color(252, 68, 0), new Color(252, 64, 0), new Color(252, 60, 0)
    , new Color(252, 60, 0), new Color(252, 56, 0), new Color(252, 52, 0)
    , new Color(252, 48, 0), new Color(252, 44, 0), new Color(252, 40, 0)
    , new Color(252, 36, 0), new Color(252, 32, 0), new Color(252, 28, 0)
    , new Color(252, 24, 0), new Color(252, 20, 0), new Color(252, 16, 0)
    , new Color(252, 12, 0), new Color(252, 8, 0), new Color(252, 4, 0)
    , new Color(252, 0, 0), new Color(252, 0, 0), new Color(248, 0, 0)
    , new Color(244, 0, 0), new Color(244, 0, 0), new Color(240, 0, 0)
    , new Color(236, 0, 0), new Color(232, 0, 0), new Color(232, 0, 0)
    , new Color(228, 0, 0), new Color(224, 0, 0), new Color(224, 0, 0)
    , new Color(220, 0, 0), new Color(216, 0, 0), new Color(212, 0, 0)
    , new Color(212, 0, 0), new Color(208, 0, 0), new Color(204, 0, 0)
    , new Color(204, 0, 0), new Color(200, 0, 0), new Color(196, 0, 0)
    , new Color(192, 0, 0), new Color(192, 0, 0), new Color(188, 0, 0)
    , new Color(184, 0, 0), new Color(184, 0, 0), new Color(180, 0, 0)
    , new Color(176, 0, 0), new Color(172, 0, 0), new Color(172, 0, 0)
    , new Color(168, 0, 0), new Color(164, 0, 0), new Color(160, 0, 0)
    , new Color(160, 0, 0), new Color(156, 4, 4), new Color(152, 4, 4)
    , new Color(148, 8, 8), new Color(144, 8, 8), new Color(140, 12, 12)
    , new Color(140, 12, 12), new Color(136, 16, 16), new Color(132, 16, 16)
    , new Color(128, 20, 20), new Color(124, 20, 20), new Color(120, 24, 24)
    , new Color(120, 24, 24), new Color(116, 28, 28), new Color(112, 28, 28)
    , new Color(108, 32, 32), new Color(104, 32, 32), new Color(100, 36, 36)
    , new Color(100, 36, 36), new Color(96, 40, 40), new Color(92, 40, 40)
    , new Color(88, 44, 44), new Color(84, 44, 44), new Color(80, 48, 48)
    , new Color(80, 48, 48), new Color(76, 52, 52), new Color(72, 52, 52)
    , new Color(68, 56, 56), new Color(64, 56, 56), new Color(60, 60, 60)
    , new Color(60, 60, 60)
  )

