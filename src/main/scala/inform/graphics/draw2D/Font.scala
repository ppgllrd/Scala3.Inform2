/*
  Pepe Gallardo, 2022
 */

package inform.graphics.draw2D

class Font(_name: String, _style: Int = Font.PLAIN, _size: Int) extends java.awt.Font(_name, _style, _size) :
  override def toString: String = s"Font($name, $style, $size)".format(name, style, size)


object Font:
  val PLAIN: Int = java.awt.Font.PLAIN
  val BOLD: Int = java.awt.Font.BOLD
  val ITALIC: Int = java.awt.Font.ITALIC

  def apply(name: String, style: Int = Font.PLAIN, size: Int): Font =
    new Font(name, style, size)

  def unapply(f: Font): Option[(String, Int, Int)] =
    Some(f.getName, f.getStyle, f.getSize)