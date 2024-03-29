/*
  Pepe Gallardo, 2023
 */

package inform.graphics.draw2D

/** A font.
  *
  * @param _name
  *   name of font to use.
  * @param _style
  *   the style of font (Font.PLAIN, Font.BOLD or Font.ITALIC).
  * @param _size
  *   the point size of the font.
  */
class Font(_name: String, _style: Int = Font.PLAIN, _size: Int)
    extends java.awt.Font(_name, _style, _size):
  override def toString: String = s"Font($name, $style, $size)"

object Font:
  val PLAIN: Int = java.awt.Font.PLAIN
  val BOLD: Int = java.awt.Font.BOLD
  val ITALIC: Int = java.awt.Font.ITALIC

  /** Constructs a new font from specified parameters.
    * @param name
    *   name of font to use.
    * @param style
    *   the style of font (Font.PLAIN, Font.BOLD or Font.ITALIC).
    * @param size
    *   the point size of the font.
    * @return
    */
  def apply(name: String, style: Int = Font.PLAIN, size: Int): Font =
    new Font(name, style, size)

  def unapply(f: Font): (String, Int, Int) =
    (f.getName, f.getStyle, f.getSize)
