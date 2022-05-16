/******************************************************************************
 * Informática. Grado en Matemáticas. Universidad de Málaga
 * @ Pepe Gallardo
 *
 * Ventanas para gráficos
 *
 *****************************************************************************/

package inform.graphics.draw2D

import java.awt.geom.{AffineTransform, Rectangle2D}
import java.awt.image.BufferedImage
import java.awt.{Color, Dimension, RenderingHints}
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JOptionPane

import scala.swing._

object GraphicsWindow {
  private val defaultWidth = 600
  private val defaultHeight = 400
  private val defaultTitle = "Graphics Window"
  private val defaultBackgroundColor = Color.white

  private val maxCoordinate = 1000

  def apply(title : String, width : Int, height : Int, backgroundColor : Color = defaultBackgroundColor) =
    new GraphicsWindow(title, width, height, backgroundColor)

  def apply(width : Int, height : Int) =
    new GraphicsWindow(width, height)

  def apply() =
    new GraphicsWindow()

  implicit def _GraphicsWindow2g2D(v : GraphicsWindow) : Graphics2D = v.getGraphics2D

  private var windowsCounter : Int = 0
}


class GraphicsWindow(title : String, width : Int, height : Int, backgroundColor : Color = GraphicsWindow.defaultBackgroundColor) {
  def this(width : Int, height : Int) = {
    this(GraphicsWindow.defaultTitle, width, height, GraphicsWindow.defaultBackgroundColor)
  }

  def this() = {
    this (GraphicsWindow.defaultWidth, GraphicsWindow.defaultHeight)
  }

  private val menuBar = new MenuBar {
    val extension = "png"
    contents += new Menu("Fichero") {
      contents += new MenuItem(Action("Guardar gráfico") {
        val fichero = title + "." + extension
        saveToPNGFile(fichero)
        JOptionPane.showMessageDialog(mainFrame.peer
          , "Gráfico guardado en fichero " + System.getProperty("user.dir") + File.separator + fichero
          , "Guardado"
          , JOptionPane.INFORMATION_MESSAGE)
      })
      contents += new MenuItem(Action("Guardar gráfico como...") {
        val chooser = new javax.swing.JFileChooser
        val filter = new javax.swing.filechooser.FileNameExtensionFilter("Ficheros "+extension.toUpperCase, extension)
        chooser.setFileFilter(filter)
        val resultado = chooser.showOpenDialog(mainFrame.peer)
        if (resultado == javax.swing.JFileChooser.APPROVE_OPTION) {
          var fichero = chooser.getSelectedFile.getAbsolutePath
          if(!fichero.toLowerCase.endsWith("." + extension))
            fichero += "." + extension
          saveToPNGFile(fichero)
        }
      })
      contents += new Separator
      contents += new MenuItem(Action("Acerca de...") {
        JOptionPane.showMessageDialog(mainFrame.peer
          , "Véntana gráfica de inform.graphics\n@Pepe Gallardo"
          , "Acerca de..."
          , JOptionPane.INFORMATION_MESSAGE)
      })
      contents += new Separator
      contents += new MenuItem(Action("Salir") {
        System.exit(0)
      })
    }
  }

  private lazy val originalWidth = width
  private lazy val originalHeight = height + menuBar.size.height

  private var drawingProcedure : (Graphics2D => Unit) = null

  private val panel : Panel = new Panel {
    opaque = true
    background = backgroundColor
    peer.setDoubleBuffered(true)

    override def paintComponent(g2D: Graphics2D): Unit = {
      super.paintComponent(g2D)
      if (drawingProcedure != null) {
        /*
        val width = size.width
        val height = size.height

        println(clearBeforeDrawing)
        if(clearBeforeDrawing) {
          g2D.setColor(backgroundColor)
          g2D.fillRect(0, 0, width, height)
        }
        */
        g2D.setColor(Color.black)
        setAntialias(g2D)
        drawingProcedure(g2D)
      } else if(image != null) {
        val w = image.getWidth
        val h = image.getHeight
        g2D.translate((size.width-w)/2, (size.height-h)/2)
        g2D.drawImage(image, 0, 0, null)
      }
    }
  }

  GraphicsWindow.windowsCounter += 1

  private val mainFrame = new MainFrame {
    val offset = 20
    location = new Point(offset*GraphicsWindow.windowsCounter, offset*GraphicsWindow.windowsCounter)
    contents = panel

    override def closeOperation(): Unit = {
      GraphicsWindow.windowsCounter -= 1
      if (GraphicsWindow.windowsCounter == 0)
        System.exit(0)
    }
  }

  mainFrame.title = title
  mainFrame.menuBar = menuBar
  mainFrame.preferredSize = new Dimension(originalWidth, originalHeight)
  mainFrame.visible = true
  mainFrame.pack()

  private val factor = (originalWidth max originalHeight).toDouble/GraphicsWindow.maxCoordinate*0.99
  private var programmedScale = 1.0
  private var windowRedimensionedScale = 1.0

  def scale: Radian = programmedScale

  def scale_=(esc : Double): Unit = {
    programmedScale = esc
    val transform = mkTransform(panel.size.width, panel.size.height)
    imageG2D.setTransform(transform)
  }

  private def mkTransform(width : Int, height : Int) : AffineTransform = {
    val transform = new AffineTransform()
    transform.translate(width/2, height/2)
    transform.scale(programmedScale*windowRedimensionedScale*factor, programmedScale*windowRedimensionedScale*factor)
    return transform
  }

  private val image = new BufferedImage(panel.size.width, panel.size.height, BufferedImage.TYPE_INT_ARGB)
  private val imageG2D : Graphics2D = image.createGraphics()

  def getGraphics2D : Graphics2D = imageG2D

  scale_=(1.0)

  private def setAntialias(g2D : Graphics2D): Unit = {
    g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON)
  }

  setAntialias(imageG2D)
  imageG2D.setColor(Color.black)

  def clear(): Unit = {
    val color = imageG2D.getColor
    val transform = imageG2D.getTransform

    val invTrs = transform.createInverse()
    imageG2D.transform(invTrs)

    imageG2D.setColor(backgroundColor)
    imageG2D.fillRect(0, 0, originalWidth, originalHeight)

    imageG2D.transform(transform)
    imageG2D.setColor(color)
  }

  clear()

  /**
   * Draw contents of windows using drawing procedure.
   * @param procedure drawing procedure used to draw contents of windows.
   */
  def drawWith(procedure : Graphics2D => Unit): Unit = {
    drawingProcedure =
      g2D => {
        val w = panel.size.width
        val h = panel.size.height

        windowRedimensionedScale = (w.toDouble / originalWidth.toDouble) min (h.toDouble / originalHeight.toDouble)

        g2D.transform(mkTransform(w, h))
        procedure(g2D)
      }
    panel.repaint()
  }

  import reflect.Selectable.reflectiveSelectable
  def jFreeChartDraw(chart : {def draw(g2D : Graphics2D, rect : Rectangle2D): Unit}): Unit = {
    drawingProcedure =
      g2D => {
        val w = panel.size.width
        val h = panel.size.height
        chart.draw(g2D, new Rectangle(0, 0, w, h))
      }
    panel.repaint()
  }

  def draw(procedure : Graphics2D => Unit): Unit = { drawWith(procedure) }

  def draw(chart : {def draw(g2D : Graphics2D, rect : Rectangle2D): Unit}): Unit = { jFreeChartDraw(chart) }

  def saveToPNGFile(file : File): Unit = {
    val w = panel.size.width
    val h = panel.size.height
    val bi : BufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)
    val g2D : Graphics2D = bi.createGraphics()

    panel.paint(g2D)
    ImageIO.write(bi, "PNG", file)
    g2D.dispose()
  }

  def saveToPNGFile(fileName : String): Unit = {
    saveToPNGFile(new File(fileName))
  }

  /**
   * Updates contents of this graphic window.
   */
  def update(): Unit = {
    panel.repaint()
  }
}

