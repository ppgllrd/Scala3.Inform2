/*
  Pepe Gallardo, 2022
 */

package inform.photo

import scala.swing._
import java.awt.image._

import javax.imageio.ImageIO
import java.io.{File, IOException}
import java.net.URL
import java.awt.FileDialog

import inform.graphics.color
import javax.swing.{ImageIcon, JOptionPane}

class Photo private(private val image: BufferedImage):
  /** number of rows of Photo */
  val rows: Int = image.getHeight

  /** number of columns of Photo */
  val columns: Int = image.getWidth

  if columns <= 0 || rows <= 0 then
    throw new RuntimeException(s"Photo dimensions must be positive: $rows x $columns.")

  /** number of rows of Photo */
  val height: Int = rows

  /** number of columns of Photo */
  val width: Int = columns

  // set to TYPE_INT_ARGB to support transparency
  //private var image: BufferedImage = new BufferedImage(columnas, filas, BufferedImage.TYPE_INT_RGB)

  private var fileName = s"Photo.${Photo.numWindows}.$rows-by-$columns.png"

  /** Creates a new white Photo with given dimensions.
   */
  def this(rows: Int, columns: Int) =
    this(new BufferedImage(columns, rows, BufferedImage.TYPE_INT_RGB))
    val g2D: Graphics2D = image.createGraphics()
    g2D.setColor(new Color(255, 255, 255))
    g2D.fillRect(0, 0, columns, rows)
    g2D.dispose()


  /** Creates a new Photo from a file (with format JPG, JPEG, PNG, BMP, GIF, etc.).
   */
  def this(fileName: String) =
    this(Photo.imageFromFile(fileName))
    this.fileName = fileName

  private var frame: MainFrame = null

  private def createFrameIfNonExistent(): Unit =
    if frame == null then
      Photo.numWindows += 1
      frame = new MainFrame:
        override def closeOperation(): Unit =
          Photo.numWindows -= 1
          if Photo.numWindows == 0 then
            System.exit(0)

        contents =
          if image == null then
            null // no image available
          else
            new Label:
              icon = new ImageIcon(image)

        private val coordinates = 30 * Photo.numWindows
        location = new Point(coordinates, coordinates)
        visible = true
        resizable = false
        //Swing.onEDT(this)
        title = fileName

      frame.menuBar = menuBar
      frame.pack()



  /** Shows Photo on screen.
   */
  def show(): Unit =
    createFrameIfNonExistent()
    frame.repaint()


  /** Updates Photo on screen with its current state.
   */
  def update(): Unit =
    show()


  private val menuBar: MenuBar = new MenuBar:
    val extension1 = "png"
    val extension2 = "jpg"
    contents += new Menu("File"):
      contents += new MenuItem(Action("Save") {
        saveAs(fileName, showDialog = true)
      })
      contents += new MenuItem(Action("Save as...") {
        val chooser = new javax.swing.JFileChooser
        val filter = new javax.swing.filechooser.FileNameExtensionFilter(s"Files ${extension1.toUpperCase} ${extension2.toUpperCase}", extension1, extension2)
        chooser.setFileFilter(filter)
        val result = chooser.showOpenDialog(frame.peer)
        if result == javax.swing.JFileChooser.APPROVE_OPTION then
          var fileName = chooser.getSelectedFile.getAbsolutePath
          if !fileName.toLowerCase.endsWith("." + extension1) && !fileName.toLowerCase.endsWith("." + extension2) then
            fileName += s".$extension1"
          saveAs(fileName, showDialog = false)
      })
      contents += new Separator
      contents += new MenuItem(Action("About...") {
        JOptionPane.showMessageDialog(frame.peer
          , "inform.graphics.photo\n@Pepe Gallardo"
          , "About..."
          , JOptionPane.INFORMATION_MESSAGE)
      })
      contents += new Separator
      contents += new MenuItem(Action("Exit") {
        System.exit(0)
      })


  /** Saves Photo to a file. */
  def saveAs(name: String, showDialog: Boolean = false): Unit =
    saveToFile(new File(name), showDialog)


  /** Saves Photo to a file. Prompts for file name. */
  def save(): Unit =
    val chooser = new FileDialog(frame.peer, "Use .png or .jpg extension", FileDialog.SAVE)
    chooser.setVisible(true)
    if chooser.getFile != null then
      saveAs(chooser.getDirectory + File.separator + chooser.getFile)


  private def extension(fileName: String) =
    fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase()


  /** Saves Photo to a file. */
  def saveToFile(file: File, showDialog: Boolean = false): Unit =
    val suffix = extension(fileName)

    if suffix.equals("jpg") || suffix.equals("png") then
      try {
        ImageIO.write(image, suffix, file)
        val fileName = file.getName
        if showDialog then
          JOptionPane.showMessageDialog(frame.peer
            , s"Photo saved in file ${System.getProperty("user.dir")} ${File.separator} $fileName"
            , "Saved"
            , JOptionPane.INFORMATION_MESSAGE)
        this.fileName = fileName
        frame.title = fileName
      }
      catch {
        case e: IOException =>
          e.printStackTrace()
      }
    else
      JOptionPane.showMessageDialog(frame.peer, "File name should end with .jpg or .png", "Error", JOptionPane.ERROR_MESSAGE)


  private def errorMessage(msg: String, i: Int, j: Int) =
    s"$msg.\nTrying to access pixel row=$i column=$j.\nPhoto dimensions are ${height}x$width.\nRow must be in range 0 to ${height - 1}.\nColumn must be in range 0 a ${width - 1}."


  private def get(i: Int, j: Int): inform.graphics.color.Color =
    try {
      new inform.graphics.color.Color(image.getRGB(j, i))
    } catch {
      case _: Throwable => throw new IndexOutOfBoundsException(errorMessage("Error reading pixel in photo", i, j))
    }

  private def set(i: Int, j: Int, color: Color): Unit =
    if color == null then
      throw new RuntimeException("Error modifying pixel in photo: provided color is null")
    else
      try {
        image.setRGB(j, i, color.getRGB)
      } catch {
        case _: Throwable => throw new IndexOutOfBoundsException(errorMessage("Error modifying pixel in photo", i, j))
      }

  case class PhotoRow(row: Int):
    /**
     *
     * @return Pixel in provided column of this row.
     */
    def apply(column: Int): inform.graphics.color.Color = get(row, column)

    /**
     * Sets color of pixel at provided column of this row.
     */
    def update(column: Int, color: Color): Unit = set(row, column, color)


  /** @return This row from Photo. */
  def apply(row: Int): PhotoRow = new PhotoRow(row)

  /** Modifies pixel at provided row and column with provided color.
   */
  def update(row: Int, column: Int, color: Color): Unit = set(row, column, color)



object Photo:
  private var numWindows: Int = 0

  /** Creates a new white Photo with given dimensions.
   */
  def apply(rows: Int, columns: Int) =
    new Photo(rows, columns)


  /** Creates a new white Photo with given dimensions.
   */
  def ofDim(rows: Int, columns: Int) =
    new Photo(rows, columns)


  /** Creates a new Photo from a file (with format JPG, JPEG, PNG, BMP, GIF, etc.).
   */
  def apply(fileName: String) =
    new Photo(fileName)


  /** Creates a new Photo from a file (with format JPG, JPEG, PNG, BMP, GIF, etc.).
   */
  def fromFile(fileName: String) =
    new Photo(fileName)


  private def imageFromFile(fileName: String): BufferedImage =
    val file = new File(fileName)
    try {
      val image =
        if file.isFile then
          ImageIO.read(file)
        else
          var url = getClass.getResource(fileName)
          if url == null then
            url = new URL(fileName)
          ImageIO.read(url)

      // check that image was read in
      if image == null then
        throw new RuntimeException(s"Invalid file name: $fileName")

      image
    } catch {
      case _: IOException =>
        throw new RuntimeException(s"File $fileName doesn't exists.")
    }





