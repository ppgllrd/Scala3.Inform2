/******************************************************************************
 * Informática. Grado en Matemáticas. Universidad de Málaga
 * @ Pepe Gallardo
 *
 * Robot
 *
 *****************************************************************************/

package inform.robot

import java.awt._
import java.awt.event._
import java.awt.image._
import java.io._
import java.net._
import javax.imageio._
import javax.swing._

private class Grid extends JComponent with KeyListener with MouseListener {
  private var cells: Array[Array[Cell]] = null
  private var frame: JFrame = null
  private var lastKeyPressed: Int = 0
  private var lastLocationClicked: Location = null
  private var lineColor: Color = null

  def this(numRows: Int, numCols: Int) = {
    this()
    init(numRows, numCols)
  }

  def this(assetFile: String) = {
    this()
    val image: BufferedImage = loadImage(assetFile)
    init(image.getHeight(), image.getWidth())
    showImage(image)
    setTitle(assetFile)
  }

  private def init(numRows: Int, numCols: Int): Unit = {
    lastKeyPressed = -1
    lastLocationClicked = null
    lineColor = null

    cells = Array.fill(numRows, numCols)(new Cell())

    frame = new JFrame("Board")
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    frame.addKeyListener(this)

    val cellSize = scala.math.max(scala.math.min(750 / getNumRows, 750 / getNumCols), 1)
    setPreferredSize(new Dimension(cellSize * numCols, cellSize * numRows))
    addMouseListener(this)
    frame.getContentPane.add(this)

    frame.pack()
    frame.setVisible(true)
  }

  private def loadImage(assetFile: String) : BufferedImage = {
      val url: URL = getClass.getResource(assetFile)
      if (url == null)
        sys.error(s"cannot find file: $assetFile")
      try {
        ImageIO.read(url)
      } catch {
        case _: IOException => sys.error(s"unable to read from file: $assetFile")
      }
    }

  def getNumRows: Int = cells.length

  def getNumCols: Int = cells(0).length

  private def showImage(image: BufferedImage): Unit = {
    for (row <- 0 until getNumRows) {
      for (col <- 0 until getNumCols) {
        val x = col * image.getWidth() / getNumCols
        val y = row * image.getHeight() / getNumRows
        val c = image.getRGB(x, y)
        val red = (c & 0x00ff0000) >> 16
        val green = (c & 0x0000ff00) >> 8
        val blue = c & 0x000000ff
        cells(row)(col).color = new Color(red, green, blue)
      }
    }
    repaint()
  }

  private def getCellSize: Int = {
      val cellWidth = getWidth / getNumCols
      val cellHeight = getHeight / getNumRows
      cellWidth min cellHeight
    }

  def keyPressed(e: KeyEvent): Unit =
    lastKeyPressed = e.getKeyCode

  def keyReleased(e: KeyEvent): Unit = {
    // ignored
  }

  def keyTyped(e: KeyEvent): Unit = {
    // ignored
  }

  def mousePressed(e: MouseEvent): Unit = {
    val cellSize = getCellSize
    val row = e.getY / cellSize
    if (row < 0 || row >= getNumRows)
      return
    val col = e.getX / cellSize
    if (col < 0 || col >= getNumCols) {
      return
    }
    lastLocationClicked = Location(row, col)
  }

  def mouseReleased(e: MouseEvent): Unit = {
    // ignore
  }

  def mouseClicked(e: MouseEvent): Unit = {
    // ignore
  }

  def mouseEntered(e: MouseEvent): Unit = {
    // ignore
  }

  def mouseExited(e: MouseEvent): Unit = {
    // ignore
  }

  override def paintComponent(g: Graphics): Unit = {
    for (row <- 0 until getNumRows) {
      for (col <- 0 until getNumCols) {
        val cell = cells(row)(col)

        g.setColor(cell.color)
        val cellSize = getCellSize
        val x = col * cellSize
        val y = row * cellSize
        g.fillRect(x, y, cellSize, cellSize)

        val imageFileName = cell.assetFile
        if (imageFileName != null) {
          val url : URL = getClass.getResource(imageFileName)
          if (url == null)
            sys.error(s"File not found: $imageFileName")
          else {
            val image = new ImageIcon(url).getImage
            val width = image.getWidth(null)
            val height = image.getHeight(null)
            if (width > height) {
              val drawHeight = cellSize * height / width
              g.drawImage(image, x, y + (cellSize - drawHeight) / 2, cellSize, drawHeight, null)
            } else {
              val drawWidth = cellSize * width / height
              g.drawImage(image, x + (cellSize - drawWidth) / 2, y, drawWidth, cellSize, null)
            }
          }
        }

        if (lineColor != null) {
          g.setColor(lineColor)
          g.drawRect(x, y, cellSize, cellSize)
        }
      }
    }
  }

  def setTitle(title: String): Unit =
    frame.setTitle(title)


  def isValid(loc: Location): Boolean = {
      val Location(row, col) = loc
      0 <= row && row < getNumRows && 0 <= col && col < getNumCols
    }

  def setColor(loc: Location, color: Color): Unit = {
    if (!isValid(loc))
      sys.error(s"cannot set color of invalid location $loc to color $color")
    cells(loc.row)(loc.column).color = color
    repaint()
  }

  def getColor(loc: Location): Color = {
      if (!isValid(loc))
        sys.error(s"cannot get color from invalid location $loc")
      cells(loc.row)(loc.column).color
    }

  def setImage(loc: Location, assetFile: String): Unit = {
    if (!isValid(loc))
      sys.error(s"cannot set image for invalid location $loc to \"$assetFile\"")
    cells(loc.row)(loc.column).assetFile = assetFile
    repaint()
  }

  def getAssetFile(loc: Location): String = {
      if (!isValid(loc))
        sys.error(s"cannot get image for invalid location $loc")
      cells(loc.row)(loc.column).assetFile
    }

  def pause(milliseconds: Int): Unit = {
    try {
      Thread.sleep(milliseconds)
    } catch {
      case _: Exception => ; // ignore
    }
  }

  //returns -1 if no key pressed since last call.
  //otherwise returns the code for the last key pressed.
  def checkLastKeyPressed() : Int = {
      val key = lastKeyPressed
      lastKeyPressed = -1
      key
    }

  //returns null if no location clicked since last call.
  def checkLastLocationClicked() : Location = {
      val loc = lastLocationClicked
      lastLocationClicked = null
      loc
    }

  def load(imageFileName: String): Unit = {
    showImage(loadImage(imageFileName))
    setTitle(imageFileName)
  }

  def save(imageFileName: String): Unit = {
    try {
      val bi = new BufferedImage(getWidth, getHeight, BufferedImage.TYPE_INT_RGB)
      paintComponent(bi.getGraphics)
      val index = imageFileName.lastIndexOf('.')
      if (index == -1)
        sys.error(s"invalid image file name:  $imageFileName")
      ImageIO.write(bi, imageFileName.substring(index + 1), new File(imageFileName))
    } catch {
      case e : IOException => sys.error("unable to save image to file: $imageFileName")
    }
  }

  def setLineColor(color: Color): Unit = {
    lineColor = color
    repaint()
  }

  def showMessageDialog(message: String): Unit = {
    JOptionPane.showMessageDialog(this, message)
  }

  def showInputDialog(message: String) : String = {
    JOptionPane.showInputDialog(this, message)
  }
}