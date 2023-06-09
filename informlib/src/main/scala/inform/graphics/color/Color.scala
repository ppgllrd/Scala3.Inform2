/** **************************************************************************************
  * Informática. Grado en Matemáticas. Universidad de Málaga. \@ Pepe Gallardo
  *
  * Colors
  */

package inform.graphics.color

object Color:
  val red: Color = new Color(255, 0, 0)
  val blue: Color = new Color(0, 0, 255)
  val green: Color = new Color(0, 255, 0)
  val yellow: Color = new Color(255, 255, 0)
  val cyan: Color = new Color(0, 255, 255)
  val magenta: Color = new Color(255, 0, 255)
  val pink: Color = new Color(255, 175, 175)
  val orange: Color = new Color(255, 200, 0)
  val white: Color = new Color(255, 255, 255)
  val black: Color = new Color(0, 0, 0)
  val gray: Color = new Color(128, 128, 128)

  /** @return
    *   New color from its (Red, Green, Blue) components <a
    *   href="https://en.wikipedia.org/wiki/RGB_color_model">RGB</a>. Each parameter must be in
    *   range [0,255].
    */
  def apply(red: Int, green: Int, blue: Int): Color =
    new Color(red, green, blue)

  /** @return
    *   New color from its (Red, Green, Blue, Alpha) components <a
    *   href="https://en.wikipedia.org/wiki/RGBA_color_model">RGBA</a>. Each parameter must be in
    *   range [0,255].
    */
  def apply(red: Int, green: Int, blue: Int, alpha: Int): Color =
    new Color(red, green, blue, alpha)

  /** @return
    *   New color from its (Red, Green, Blue, Alpha) components <a
    *   href="https://en.wikipedia.org/wiki/RGBA_color_model">RGBA</a>. Each parameter must be in
    *   range [0,255].
    */
  def rgba(red: Int, green: Int, blue: Int, alpha: Int): Color =
    new Color(red, green, blue, alpha)

  /** @return
    *   New color from its (Red, Green, Blue) components <a
    *   href="https://en.wikipedia.org/wiki/RGB_color_model">RGB</a>. Each parameter must be in
    *   range [0,255].
    */
  def rgb(red: Int, green: Int, blue: Int): Color =
    new Color(red, green, blue)

  /** @return
    *   Deconstructs a color into its (Red, Green, Blue) components <a
    *   href="https://en.wikipedia.org/wiki/RGB_color_model">RGB</a>.
    */
  def unapply(c: Color): (Int, Int, Int) =
    (c.getRed, c.getGreen, c.getBlue)

  /** Color from its (Red, Green, Blue) components <a
    * href="https://en.wikipedia.org/wiki/RGB_color_model">RGB</a>. Each parameter must be in range
    * [0,255].
    */
  object RGB:
    /** @return
      *   New color from its (Red, Green, Blue) components <a
      *   href="https://en.wikipedia.org/wiki/RGB_color_model">RGB</a>. Each parameter must be in
      *   range [0,255].
      */
    def apply(red: Int, green: Int, blue: Int): Color =
      new Color(red, green, blue)

    /** @return
      *   Deconstructs a color into its (Red, Green, Blue) components <a
      *   href="https://en.wikipedia.org/wiki/RGB_color_model">RGB</a>.
      */
    def unapply(c: Color): (Int, Int, Int) =
      (c.getRed, c.getGreen, c.getBlue)

  /** Color from its (Red, Green, Blue, Alpha) components <a
    * href="https://en.wikipedia.org/wiki/RGBA_color_model">RGBA</a>. Each parameter must be in
    * range [0,255].
    */
  object RGBA:
    /** @return
      *   New color from its (Red, Green, Blue, Alpha) components <a
      *   href="https://en.wikipedia.org/wiki/RGBA_color_model">RGBA</a>. Each parameter must be in
      *   range [0,255].
      */
    def apply(red: Int, green: Int, blue: Int, alpha: Int): Color =
      new Color(red, green, blue, alpha)

    /** @return
      *   Deconstructs a color into its (Red, Green, Blue, Alpha) components <a
      *   href="https://en.wikipedia.org/wiki/RGBA_color_model">RGBA</a>.
      */
    def unapply(c: Color): (Int, Int, Int, Int) =
      (c.getRed, c.getGreen, c.getBlue, c.getAlpha)

class Color(rgba: Int) extends java.awt.Color(rgba, true):
  /** @return
    *   New color from its (Red, Green, Blue, Alpha) components <a
    *   href="https://en.wikipedia.org/wiki/RGBA_color_model">RGBA</a>. Each parameter must be in
    *   range [0,255].
    */
  def this(red: Int, green: Int, blue: Int, alpha: Int) =
    this(
      ((alpha & 0xff) << 24) | ((red & 0xff) << 16) | ((green & 0xff) << 8) | ((blue & 0xff) << 0)
    )

  /** @return
    *   New color from its (Red, Green, Blue) components <a
    *   href="https://en.wikipedia.org/wiki/RGB_color_model">RGB</a>. Each parameter must be in
    *   range [0,255].
    */
  def this(red: Int, green: Int, blue: Int) =
    this(red, green, blue, 255)

  override def toString: String =
    s"Color($getRed, $getGreen, $getBlue, $getAlpha)"
