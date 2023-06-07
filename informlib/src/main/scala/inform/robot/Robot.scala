/** ***************************************************************************************
  * Informática. Grado en Matemáticas. Universidad de Málaga. \@ Pepe Gallardo.
  *
  * Taken from Dave Feinberg Java version
  *
  * Robot
  */

package inform.robot

import java.awt.Color

object Robot {

  /** Builds a new robot that will move in provided map.
    * @param mapFile
    *   file describing map.
    * @return
    *   a new robot that will move in provided map.
    */
  def apply(mapFile: String) =
    new Robot(mapFile)

  private def asset(fileName: String) = s"/robot/$fileName"

  private final val ROBOT_NORTH_IMAGE = asset("robotnorth.gif")
  private final val ROBOT_SOUTH_IMAGE = asset("robotsouth.gif")
  private final val ROBOT_EAST_IMAGE = asset("roboteast.gif")
  private final val ROBOT_WEST_IMAGE = asset("robotwest.gif")
  private final val WALL_IMAGE = asset("wall.gif")

  private final val LIGHT_COLOR = new Color(229, 170, 122)
  private final val DARK_COLOR = new Color(153, 114, 81)
}

/** Builds a new robot that will move in provided map.
  *
  * @param mapFile
  *   file describing map.
  * @return
  *   a new robot that will move in provided map.
  */
class Robot(mapFile: String) {
  import Robot.*

  private var robotLoc: Location = null
  private var direction: Char = ' '
  private var delay = 250

  private def init(): Grid = {
    val mapReader = new MapReader(mapFile)
    val lines = new collection.mutable.ArrayBuffer[String]()
    while (mapReader.hasMoreLines)
      lines += mapReader.readLine()

    require(lines.nonEmpty, s"Map file \"$mapFile\" cannot be empty")

    val numRows: Int = lines.size
    val numCols: Int = lines.head.length
    val grid = new Grid(numRows, numCols)
    grid.setTitle(mapFile)
    grid.setLineColor(new Color(0, 0, 0))

    for (row <- 0 until numRows) {
      for (col <- 0 until numCols) {
        val loc = Location(row, col)
        val line = lines(row)
        if (line.length != numCols)
          sys.error(
            s"Inconsistent length of line \"$line\" in map \"$mapFile\""
          )

        val ch = lines(row)(col)
        if (Set.from("NSEWnsew").contains(ch)) {
          if (Set.from("NSEW").contains(ch))
            grid.setColor(loc, LIGHT_COLOR)
          else
            grid.setColor(loc, DARK_COLOR)

          ch match
            case 'N' | 'n' => grid.setImage(loc, ROBOT_NORTH_IMAGE)
            case 'S' | 's' => grid.setImage(loc, ROBOT_SOUTH_IMAGE)
            case 'E' | 'e' => grid.setImage(loc, ROBOT_EAST_IMAGE)
            case _         => grid.setImage(loc, ROBOT_WEST_IMAGE)

          robotLoc = loc
          direction = ch.toUpper
        } else if (ch == 'X')
          grid.setImage(loc, asset(WALL_IMAGE))
        else if (ch == '.')
          grid.setColor(loc, LIGHT_COLOR)
        else if (ch == ':')
          grid.setColor(loc, DARK_COLOR)
        else
          sys.error(s"Invalid character $ch in map file \"$mapFile\"")
      }
    }
    grid
  }

  private val grid: Grid = init()

  private def newLocation(loc: Location, direction: Char): Location = {
    val Location(row, col) = loc
    direction match
      case 'N' => Location(row - 1, col)
      case 'S' => Location(row + 1, col)
      case 'E' => Location(row, col + 1)
      case 'W' => Location(row, col - 1)
  }

  private def checkMapLoaded(): Unit = {
    if (grid == null)
      sys.error("Map not loaded yet")
  }

  /** Makes robot move to next cell in front of current cell. Raises an error if destination cell is
    * non-free or if it is out of map.
    */
  def forward(): Unit = {
    checkMapLoaded()

    val newLoc = newLocation(robotLoc, direction)

    if (!grid.isValid(newLoc))
      sys.error(
        s"Trying to move robot from $robotLoc to a non-valid location $newLoc"
      )
    if (grid.getAssetFile(newLoc) != null)
      sys.error(s"Trying to move robot from $robotLoc to a non-free location")

    val image = grid.getAssetFile(robotLoc)
    grid.setImage(robotLoc, null)
    robotLoc = newLoc
    grid.setImage(robotLoc, image)
    grid.pause(delay)
  }

  /** Makes robot change its direction by turning 90 degrees left.
    */
  def left(): Unit = {
    checkMapLoaded()

    direction match {
      case 'N' =>
        direction = 'W'
        grid.setImage(robotLoc, ROBOT_WEST_IMAGE)
      case 'S' =>
        direction = 'E'
        grid.setImage(robotLoc, ROBOT_EAST_IMAGE)
      case 'E' =>
        direction = 'N'
        grid.setImage(robotLoc, ROBOT_NORTH_IMAGE)
      case _ =>
        direction = 'S'
        grid.setImage(robotLoc, ROBOT_SOUTH_IMAGE)
    }
    grid.pause(delay)
  }

  /** Makes robot change its direction by turning 90 degrees right.
    */
  def right(): Unit = {
    checkMapLoaded()

    direction match {
      case 'N' =>
        direction = 'E'
        grid.setImage(robotLoc, ROBOT_EAST_IMAGE)
      case 'S' =>
        direction = 'W'
        grid.setImage(robotLoc, ROBOT_WEST_IMAGE)
      case 'E' =>
        direction = 'S'
        grid.setImage(robotLoc, ROBOT_SOUTH_IMAGE)
      case _ =>
        direction = 'N'
        grid.setImage(robotLoc, ROBOT_NORTH_IMAGE)
    }
    grid.pause(delay)
  }

  /** Makes robot lighten current cell. Raises an error if cell is already light.
    */
  def lighten(): Unit = {
    checkMapLoaded()

    if (isOnLight)
      sys.error(s"lighten: location $robotLoc is already light")
    grid.setColor(robotLoc, LIGHT_COLOR)
    grid.pause(delay)
  }

  /** Makes robot darken current cell. Raises an error if cell is already dark.
    */
  def darken(): Unit = {
    checkMapLoaded()

    if (isOnDark)
      sys.error(s"darken: location $robotLoc is already dark")
    grid.setColor(robotLoc, DARK_COLOR)
    grid.pause(delay)
  }

  /** @return
    *   true if robot is currently on a dark cell.
    */
  def isOnDark: Boolean = {
    checkMapLoaded()

    grid.getColor(robotLoc).equals(DARK_COLOR)
  }

  /** @return
    *   true if robot is currently on a light cell.
    */
  def isOnLight: Boolean = {
    checkMapLoaded()

    grid.getColor(robotLoc).equals(LIGHT_COLOR)
  }

  /** @return
    *   true if robot can move one cell forward from its current location and direction. Checks if
    *   destination cell is free and within the map.
    */
  def canMoveForward: Boolean = {
    checkMapLoaded()

    val pos = newLocation(robotLoc, direction)
    grid.isValid(pos) && grid.getAssetFile(pos) == null
  }

  /** Slows down the animation.
    * @param milliseconds
    *   Time (in milliseconds) that robot takes to perform a single action.
    */
  def setDelay(milliseconds: Int): Unit = {
    delay = milliseconds
  }
}
