/** **************************************************************************************
  * Informática. Grado en Matemáticas. Universidad de Málaga. \@ Pepe Gallardo
  *
  * Graphics Windows
  */

package inform.graphics

import java.awt.geom.{AffineTransform, Rectangle2D}
import java.awt.image.BufferedImage
import java.awt.{Color, Dimension, RenderingHints}
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JOptionPane
import scala.swing.*

object GraphicsWindow {
  private val defaultWidth = 600
  private val defaultHeight = 400
  private val defaultTitle = "Graphics Window"
  private val defaultBackgroundColor = Color.white

  private val maxCoordinate = 1000

  /** Creates a new graphic window configured with specified parameters.
    *
    * @param title
    *   title of window.
    * @param width
    *   width in pixels of window.
    * @param height
    *   height in pixels of window.
    * @param backgroundColor
    *   background color of window.
    * @return
    *   a new graphic window configured with specified parameters.
    */
  def apply(
      title: String,
      width: Int,
      height: Int,
      backgroundColor: Color = defaultBackgroundColor
  ) =
    new GraphicsWindow(title, width, height, backgroundColor)

  /** Creates a new graphic window configured with specified parameters.
    *
    * @param width
    *   width in pixels of window.
    * @param height
    *   height in pixels of window.
    * @return
    *   a new graphic window configured with specified parameters.
    */
  def apply(width: Int, height: Int) =
    new GraphicsWindow(width, height)

  /** Creates a new graphic window configured with default parameters.
    *
    * @return
    *   a new graphic window configured with default parameters.
    */
  def apply() =
    new GraphicsWindow()

  import scala.language.implicitConversions
  implicit def _GraphicsWindow2g2D(w: GraphicsWindow): Graphics2D =
    w.getGraphics2D

  private var windowsCounter: Int = 0
}

/** A new graphic window configured with specified parameters.
  *
  * @param title
  *   title of window.
  * @param width
  *   width in pixels of window.
  * @param height
  *   height in pixels of window.
  * @param backgroundColor
  */
class GraphicsWindow(
    title: String,
    width: Int,
    height: Int,
    backgroundColor: Color = GraphicsWindow.defaultBackgroundColor
) {

  /** Creates a new graphic window configured with specified parameters.
    *
    * @param width
    *   width in pixels of window.
    * @param height
    *   height in pixels of window.
    */
  def this(width: Int, height: Int) =
    this(
      GraphicsWindow.defaultTitle,
      width,
      height,
      GraphicsWindow.defaultBackgroundColor
    )

  /** Creates a new graphic window configured with default parameters.
    */
  def this() =
    this(GraphicsWindow.defaultWidth, GraphicsWindow.defaultHeight)

  private lazy val originalWidth = width
  private lazy val originalHeight = height + menuBar.size.height

  private var drawingProcedure: Graphics2D => Unit = null

  private val panel: Panel = new Panel {
    opaque = true
    background = backgroundColor
    peer.setDoubleBuffered(true)

    override def paintComponent(g2D: Graphics2D): Unit = {
      super.paintComponent(g2D)
      if (drawingProcedure != null) {
        g2D.setColor(Color.black)
        setAntialias(g2D)
        drawingProcedure(g2D)
      } else if (image != null) {
        val w = image.getWidth
        val h = image.getHeight
        g2D.translate((size.width - w) / 2, (size.height - h) / 2)
        g2D.drawImage(image, 0, 0, null)
      }
    }
  }

  private val mainFrame: MainFrame = new MainFrame {
    val offset = 20
    location = new Point(
      offset * GraphicsWindow.windowsCounter,
      offset * GraphicsWindow.windowsCounter
    )
    contents = panel

    override def closeOperation(): Unit = {
      GraphicsWindow.windowsCounter -= 1
      if (GraphicsWindow.windowsCounter == 0)
        System.exit(0)
    }
  }

  private val menuBar: MenuBar = new MenuBar {
    val extension = "png"
    contents += new Menu("File") {
      contents += new MenuItem(Action("Save graphics") {
        val fileName = title + "." + extension
        saveToPNGFile(fileName)
        JOptionPane.showMessageDialog(
          mainFrame.peer,
          "Graphics saved in file " + System.getProperty(
            "user.dir"
          ) + File.separator + fileName,
          "Saved",
          JOptionPane.INFORMATION_MESSAGE
        )
      })
      contents += new MenuItem(Action("Save graphics as...") {
        val chooser = new javax.swing.JFileChooser
        val filter = new javax.swing.filechooser.FileNameExtensionFilter(
          "Ficheros " + extension.toUpperCase,
          extension
        )
        chooser.setFileFilter(filter)
        val result = chooser.showOpenDialog(mainFrame.peer)
        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
          var fileName = chooser.getSelectedFile.getAbsolutePath
          if (!fileName.toLowerCase.endsWith("." + extension))
            fileName += "." + extension
          saveToPNGFile(fileName)
        }
      })
      contents += new Separator
      contents += new MenuItem(Action("About...") {
        JOptionPane.showMessageDialog(
          mainFrame.peer,
          "Graphics Window for inform.graphics\n@Pepe Gallardo",
          "About...",
          JOptionPane.INFORMATION_MESSAGE
        )
      })
      contents += new Separator
      contents += new MenuItem(Action("Exit") {
        System.exit(0)
      })
    }
  }

  GraphicsWindow.windowsCounter += 1

  mainFrame.title = title
  mainFrame.menuBar = menuBar
  mainFrame.preferredSize = new Dimension(originalWidth, originalHeight)
  mainFrame.visible = true
  mainFrame.pack()

  private val factor =
    (originalWidth max originalHeight).toDouble / GraphicsWindow.maxCoordinate * 0.99
  private var programmedScale = 1.0
  private var windowResizedScale = 1.0

  def scale: Double = programmedScale

  def scale_=(scl: Double): Unit = {
    programmedScale = scl
    val transform = mkTransform(panel.size.width, panel.size.height)
    imageG2D.setTransform(transform)
  }

  private def mkTransform(width: Int, height: Int): AffineTransform = {
    val transform = new AffineTransform()
    transform.translate(width / 2, height / 2)
    transform.scale(
      programmedScale * windowResizedScale * factor,
      programmedScale * windowResizedScale * factor
    )
    transform
  }

  private val image: BufferedImage = new BufferedImage(
    panel.size.width,
    panel.size.height,
    BufferedImage.TYPE_INT_ARGB
  )
  private val imageG2D: Graphics2D = image.createGraphics()

  def getGraphics2D: Graphics2D = imageG2D

  scale_=(1.0)

  private def setAntialias(g2D: Graphics2D): Unit = {
    g2D.setRenderingHint(
      RenderingHints.KEY_RENDERING,
      RenderingHints.VALUE_RENDER_QUALITY
    )
    g2D.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON
    )
  }

  setAntialias(imageG2D)
  imageG2D.setColor(Color.black)

  /** Erase the contents of this graphic window.
    */
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

  /** Draw contents of windows using provided drawing procedure.
    * @param procedure
    *   drawing procedure used to draw contents of windows.
    */
  def drawWith(procedure: Graphics2D => Unit): Unit = {
    drawingProcedure = g2D => {
      val w = panel.size.width
      val h = panel.size.height
      windowResizedScale =
        (w.toDouble / originalWidth.toDouble) min (h.toDouble / originalHeight.toDouble)

      g2D.transform(mkTransform(w, h))
      procedure(g2D)
    }
    panel.repaint()
  }

  import scala.reflect.Selectable.reflectiveSelectable
  def jFreeChartDraw(chart: {
    def draw(g2D: Graphics2D, rect: Rectangle2D): Unit
  }): Unit = {
    drawingProcedure = g2D => {
      val w = panel.size.width
      val h = panel.size.height
      chart.draw(g2D, new Rectangle(0, 0, w, h))
    }
    panel.repaint()
  }

  /** Draw contents of graphic window using provided drawing procedure.
    *
    * @param procedure
    *   drawing procedure to draw contents of graphic window.
    */
  def draw(procedure: Graphics2D => Unit): Unit = drawWith(procedure)

  def draw(
      chart: { def draw(g2D: Graphics2D, rect: Rectangle2D): Unit }
  ): Unit = jFreeChartDraw(chart)

  /** Save contents of graphic window to a PNG file.
    *
    * @param file
    *   file where contents of graphic window should be saved.
    */
  def saveToPNGFile(file: File): Unit = {
    val w = panel.size.width
    val h = panel.size.height
    val bi: BufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)
    val g2D: Graphics2D = bi.createGraphics()

    panel.paint(g2D)
    ImageIO.write(bi, "PNG", file)
    g2D.dispose()
  }

  /** Save contents of graphic window to /**
    *
    * @param chart
    *   /PNG file.
    *
    * @param fileName
    *   name of file where contents of graphic window should be saved.
    */
  def saveToPNGFile(fileName: String): Unit = {
    saveToPNGFile(new File(fileName))
  }

  /** Update contents of this graphic window.
    */
  def update(): Unit =
    panel.repaint()
}
