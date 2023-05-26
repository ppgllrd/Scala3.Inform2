/*
  Pepe Gallardo, 2022
 */

package draw2D

@main def rectanglesTest1(): Unit =
  import inform.graphics.draw2D.*
  import inform.graphics.color.*

  def draw(g2D: Graphics2D): Unit =
    g2D.setColor(Color.red)
    g2D.draw(Rectangle(-250, -200, 250, 400))

    g2D.setColor(Color.green)
    g2D.fill(Rectangle(50, 10, 300, 200))

    val rect = Rectangle(50, -200, 175, 175)
    val stroke = Stroke(10)
    g2D.setStroke(stroke)
    g2D.setColor(Color.yellow)
    g2D.fill(rect)
    g2D.setColor(Color.blue)
    g2D.draw(rect)

  val window = GraphicsWindow(450, 450)
  window.drawWith(draw)


@main def rectanglesTest2(): Unit =
  import inform.graphics.draw2D.*
  import inform.graphics.color.*

  val window = GraphicsWindow(450, 450)

  window.setColor(Color.red)
  window.draw(Rectangle(-250, -200, 250, 400))

  window.setColor(Color.green)
  window.fill(Rectangle(50, 10, 300, 200))

  val rect = Rectangle(50, -200, 175, 175)
  val stroke = Stroke(10)
  window.setStroke(stroke)
  window.setColor(Color.yellow)
  window.fill(rect)
  window.setColor(Color.blue)
  window.draw(rect)

  window.update()


@main def ellipsesTest(): Unit =
  import inform.graphics.draw2D.*
  import inform.graphics.color.*

  def draw(g2D: Graphics2D): Unit =
    g2D.setColor(Color.red)
    g2D.draw(Ellipse(-250, -200, 250, 400))

    g2D.setColor(Color.green)
    g2D.fill(Ellipse(50, 10, 300, 200))

    val rect = Ellipse(50, -200, 175, 175)
    val stroke = Stroke(10)
    g2D.setStroke(stroke)
    g2D.setColor(Color.yellow)
    g2D.fill(rect)
    g2D.setColor(Color.blue)
    g2D.draw(rect)

  val window = GraphicsWindow(450, 450)
  window.drawWith(draw)


@main def lineArcTextTest(): Unit =
  import inform.graphics.draw2D.*
  import inform.graphics.color.*
  import scala.math.*

  def draw(g2D: Graphics2D): Unit =
    val stroke = Stroke(5)
    g2D.setStroke(stroke)
    g2D.setColor(Color.red)
    g2D.draw(Line(-200, 100, -100, -150))

    g2D.setColor(Color.green)
    g2D.draw(Arc(50, -250, 300, 200, Pi/2, 3*Pi/2, Arc.OPEN))

    val font = Font("Arial", Font.ITALIC, 75)
    g2D.setFont(font)
    g2D.setColor(Color.magenta)
    g2D.drawString("Arc", 100, 30)
    g2D.drawString("Line", -275, 175)

  val window = GraphicsWindow(450, 450)
  window.drawWith(draw)


@main def polygonsTest(): Unit =
  import inform.graphics.draw2D.*
  import inform.graphics.color.*

  def draw(g2D: Graphics2D): Unit =
    val triangle = Polygon((-60, -40), (0,100), (60, -40))
    val stroke = Stroke(5)
    g2D.setStroke(stroke)

    g2D.setColor(Color.red)
    g2D.draw(triangle)

    val t1 = Transform.scale(1, 3) * Transform.translate(-180, 0)
    g2D.setColor(Color.green)
    g2D.draw(t1(triangle))

    val t2 = Transform.rotate(math.Pi / 2) * Transform.translate(0, -200)
    g2D.setColor(Color.blue)
    g2D.draw(t2(triangle))

  val window = GraphicsWindow(450, 450)
  window.drawWith(draw)


@main def alphaTest(): Unit =
  import inform.graphics.draw2D.*
  import inform.graphics.color.*

  def draw(g2D: Graphics2D): Unit =
    def drawEllipse(dx: Double, dy: Double, rx: Double, ry: Double, alpha: Double, c: Color): Unit =
      g2D.setColor(c)
      val ellipse = Ellipse(-rx, -ry, 2 * rx, 2 * ry)
      val t = Transform.rotate(alpha) * Transform.translate(dx, dy)
      g2D.fill(t(ellipse))

    val opacity = 100 // between 0 and 255
    val red = Color.RGBA(255, 0, 0, opacity)
    val green = Color.RGBA(0, 255, 0, opacity)
    val blue = Color.RGBA(0, 0, 255, opacity)
    val yellow = Color.RGBA(255, 255, 0, opacity)

    val colors = Array(red, green, blue, yellow)

    val numEllipses = 12
    for i <- 0 until numEllipses do
      drawEllipse(225,0,100,80,2 * math.Pi * i / numEllipses, colors(i % colors.length))

  val window = GraphicsWindow(450, 450)
  window.drawWith(draw)