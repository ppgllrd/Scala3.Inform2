/** **************************************************************************************
  * Informática. Grado en Matemáticas. Universidad de Málaga. \@ Pepe Gallardo
  *
  * Photos
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
import java.nio.file.Path

/** A digital photograph.
  *
  * @param image
  */
class Photo private (private val image: BufferedImage):
  /** Number of rows of Photo. */
  val rows: Int = image.getHeight

  /** Number of columns of Photo. */
  val columns: Int = image.getWidth

  if columns <= 0 || rows <= 0 then
    throw new RuntimeException(s"Photo dimensions must be positive: $rows x $columns.")

  /** Number of rows of Photo. */
  val height: Int = rows

  /** Number of columns of Photo. */
  val width: Int = columns

  // set to TYPE_INT_ARGB to support transparency
  // private var image: BufferedImage = new BufferedImage(columnas, filas, BufferedImage.TYPE_INT_RGB)

  private var fileName = s"Photo.${Photo.numWindows}.$rows-by-$columns.png"

  /** Creates a new Photo with given dimensions.
    *
    * @param rows
    *   number of rows of new photograph.
    * @param columns
    *   number of columns of new photograph.
    */
  def this(rows: Int, columns: Int) =
    this(new BufferedImage(columns, rows, BufferedImage.TYPE_INT_RGB))
    val g2D: Graphics2D = image.createGraphics()
    g2D.setColor(new Color(255, 255, 255))
    g2D.fillRect(0, 0, columns, rows)
    g2D.dispose()

  /** Creates a new Photo from provided source (with format JPG, JPEG, PNG, BMP, GIF, etc.).
    *
    * @param source
    *   path of file used for creating new photograph.
    */
  def this(source: String) =
    this(Photo.imageFromFile(source))
    this.fileName = source

  private var frame: MainFrame = null

  private def createFrameIfNonExistent(): Unit =
    if frame == null then
      Photo.numWindows += 1
      frame = new MainFrame:
        override def closeOperation(): Unit =
          Photo.numWindows -= 1
          if Photo.numWindows == 0 then System.exit(0)

        contents =
          if image == null then null // no image available
          else
            new Label:
              icon = new ImageIcon(image)

        private val coordinates = 30 * Photo.numWindows
        location = new Point(coordinates, coordinates)
        visible = true
        resizable = false
        // Swing.onEDT(this)
        title = fileName

      frame.menuBar = menuBar
      frame.pack()

  /** Shows Photo on screen.
    */
  def show(): Unit =
    createFrameIfNonExistent()
    frame.repaint()

  /** Updates Photo on screen showing its current state.
    */
  def update(): Unit =
    show()

  import Photo.*
  private val menuBar: MenuBar = new MenuBar:
    contents += new Menu("File"):
      contents += new MenuItem(Action("Save") {
        saveAs(fileName, showDialog = true)
      })
      contents += new MenuItem(Action("Save as...") {
        val chooser = new javax.swing.JFileChooser
        val filter = new javax.swing.filechooser.FileNameExtensionFilter(
          s"Files ${extension1.toUpperCase} ${extension2.toUpperCase}",
          extension1,
          extension2
        )
        chooser.setFileFilter(filter)
        val result = chooser.showOpenDialog(frame.peer)
        if result == javax.swing.JFileChooser.APPROVE_OPTION then
          var fileName = chooser.getSelectedFile.getAbsolutePath
          if !fileName.toLowerCase.endsWith("." + extension1) && !fileName.toLowerCase.endsWith(
              "." + extension2
            )
          then fileName += s".$extension1"
          saveAs(fileName, showDialog = false)
      })
      contents += new Separator
      contents += new MenuItem(Action("About...") {
        JOptionPane.showMessageDialog(
          frame.peer,
          "inform.graphics.photo\n@Pepe Gallardo",
          "About...",
          JOptionPane.INFORMATION_MESSAGE
        )
      })
      contents += new Separator
      contents += new MenuItem(Action("Exit") {
        System.exit(0)
      })

  /** Saves Photo to a file.
    *
    * @param fileName
    *   name of file to save photo.
    * @param showDialog
    *   whether to show a dialog for choosing name.
    */
  def saveAs(fileName: String, showDialog: Boolean = false): Unit =
    saveToFile(new File(fileName), showDialog)

  /** Saves Photo to a file. Prompts for file name. */
  def save(): Unit =
    val chooser =
      new FileDialog(frame.peer, s"Use .$extension1 or $extension2 extension", FileDialog.SAVE)
    chooser.setVisible(true)
    if chooser.getFile != null then saveAs(chooser.getDirectory + File.separator + chooser.getFile)

  private def extension(fileName: String) =
    fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase()

  /** Saves Photo to a file.
    *
    * @param file
    *   file to save photo.
    * @param showDialog
    *   whether to show a dialog for choosing name.
    */
  def saveToFile(file: File, showDialog: Boolean = false): Unit =
    val suffix = extension(fileName)

    if suffix.equals("jpg") || suffix.equals("png") then
      try {
        ImageIO.write(image, suffix, file)
        val fileName = file.getName
        if showDialog then
          JOptionPane.showMessageDialog(
            frame.peer,
            s"Photo saved in file ${System.getProperty("user.dir")} ${File.separator} $fileName",
            "Saved",
            JOptionPane.INFORMATION_MESSAGE
          )
        this.fileName = fileName
        frame.title = fileName
      } catch {
        case e: IOException =>
          e.printStackTrace()
      }
    else
      JOptionPane.showMessageDialog(
        frame.peer,
        "File name should end with .jpg or .png",
        "Error",
        JOptionPane.ERROR_MESSAGE
      )

  /** Saves Photo to a file.
    *
    * @param path
    *   path of file where to save photo.
    */
  def saveToPath(path: Path): Unit =
    saveToFile(path.toFile)

  private def errorMessage(msg: String, i: Int, j: Int) =
    s"$msg.\nTrying to access pixel row=$i column=$j.\nPhoto dimensions are ${height}x$width.\nRow must be in range 0 to ${height - 1}.\nColumn must be in range 0 a ${width - 1}."

  private def get(i: Int, j: Int): inform.graphics.color.Color =
    try {
      new inform.graphics.color.Color(image.getRGB(j, i))
    } catch {
      case _: Throwable =>
        throw new IndexOutOfBoundsException(errorMessage("Error reading pixel in photo", i, j))
    }

  private def set(i: Int, j: Int, color: Color): Unit =
    if color == null then
      throw new RuntimeException("Error modifying pixel in photo: provided color is null")
    else
      try {
        image.setRGB(j, i, color.getRGB)
      } catch {
        case _: Throwable =>
          throw new IndexOutOfBoundsException(errorMessage("Error modifying pixel in photo", i, j))
      }

  case class PhotoRow(row: Int):
    /** Returns pixel at provided column of this row.
      *
      * @param column
      *   column of pixel to get.
      *
      * @return
      *   Pixel in provided column of this row.
      */
    def apply(column: Int): inform.graphics.color.Color = get(row, column)

    /** Sets color of pixel at provided column of this row.
      * @param column
      *   column of pixel to set.
      * @param color
      *   color to set this pixel to.
      */
    def update(column: Int, color: Color): Unit = set(row, column, color)

  /** Returns a row of Photo.
    *
    * @param row
    *   row of Photo to get.
    *
    * @return
    *   Row of Photo.
    */
  def apply(row: Int): PhotoRow = PhotoRow(row)

  /** Modifies pixel at provided row and column with provided color.
    * @param row
    *   row of pixel to modify.
    * @param column
    *   column of pixel to modify.
    * @param color
    *   color to set pixel to.
    */
  def update(row: Int, column: Int, color: Color): Unit = set(row, column, color)

object Photo:
  private val extension1 = "png"
  private val extension2 = "jpg"

  private var numWindows: Int = 0

  /** Creates a new Photo with given dimensions.
    *
    * @param columns
    *   number of columns of new photograph.
    * @param rows
    *   number of rows of new photograph.
    * @return
    *   a new Photo with given dimensions.
    */
  def apply(rows: Int, columns: Int): Photo =
    new Photo(rows, columns)

  /** Creates a new Photo from provided file (with format JPG, JPEG, PNG, BMP, GIF, etc.).
    * @param file
    *   file used for creating new photograph.
    * @return
    *   a new Photo from provided file.
    */
  def apply(file: File): Photo =
    new Photo(file.getPath())

  /** Creates a new Photo from provided source (path of file or URL with format JPG, JPEG, PNG, BMP,
    * GIF, etc.).
    *
    * @param source
    *   path of file used for creating new photograph.
    * @return
    *   a new Photo from provided source.
    */
  def apply(source: String): Photo =
    new Photo(source)

  /** Creates a new Photo from provided path (with format JPG, JPEG, PNG, BMP, GIF, etc.).
    *
    * @param path
    *   path of file used for creating new photograph.
    * @return
    *   a new Photo from provided path.
    */
  def apply(path: Path): Photo =
    apply(path.toFile())

  /** Creates a new Photo with given dimensions.
    *
    * @param rows
    *   number of rows of new photograph.
    * @param columns
    *   number of columns of new photograph.
    * @return
    *   a new Photo with given dimensions.
    */
  def ofDim(rows: Int, columns: Int): Photo =
    new Photo(rows, columns)

  /** Creates a new Photo from provided source (path of file or URL with format JPG, JPEG, PNG, BMP,
    * GIF, etc.).
    *
    * @param source
    *   path of file used for creating new photograph.
    * @return
    *   a new Photo from provided source.
    */
  def from(source: String): Photo =
    new Photo(source)

  /** Creates a new Photo from provided file (with format JPG, JPEG, PNG, BMP, GIF, etc.).
    * @param file
    *   file used for creating new photograph.
    * @return
    *   a new Photo from provided file.
    */
  def fromFile(file: File): Photo =
    new Photo(file.getPath())

  /** Creates a new Photo from provided path (with format JPG, JPEG, PNG, BMP, GIF, etc.).
    *
    * @param path
    *   path of file used for creating new photograph.
    * @return
    *   a new Photo from provided path.
    */
  def fromPath(path: Path): Photo =
    apply(path)

  private def imageFromFile(source: String): BufferedImage =
    val file = new File(source)
    try
      val image =
        if file.isFile then ImageIO.read(file)
        else
          var url = getClass.getResource(source)
          if url == null then url = new URL(source)
          ImageIO.read(url)

      // check that image was read in
      if image == null then throw new RuntimeException(s"Invalid file name: $source")

      image
    catch
      case _: IOException =>
        throw new RuntimeException(s"File $source doesn't exists.")
