/** **************************************************************************************
  * Informática. Grado en Matemáticas. Universidad de Málaga. \@ Pepe Gallardo
  *
  * Robot
  */

package inform.robot

import java.awt.Color

private class Cell(private var _color: Color, private var _assetFile: String) {
  def this() =
    this(new Color(0, 0, 0), null)

  def color: Color = _color
  def color_=(c: Color): Unit = _color = c

  def assetFile: String = _assetFile
  def assetFile_=(file: String): Unit = _assetFile = file

  override def toString: String =
    s"Cell($color, $assetFile)"
}
