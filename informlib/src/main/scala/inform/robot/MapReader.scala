/** **************************************************************************************
  * Informática. Grado en Matemáticas. Universidad de Málaga. \@ Pepe Gallardo
  *
  * Robot
  */

package inform.robot

import java.io._

/** A line-by-line reader for robot map files.
  *
  * @param fileName
  *   path to the map file to read.
  */
private class MapReader(fileName: String) {
  private var in: BufferedReader = null
  private var line: String = null

  try {
    in = new BufferedReader(new java.io.FileReader(fileName))
    line = in.readLine()
  } catch {
    case e: IOException => throw new RuntimeException(e)
  }

  /** Checks whether there are more lines to read.
    * @return
    *   true if there are more lines available.
    */
  def hasMoreLines: Boolean = line != null

  /** Reads the current line and advances to the next one.
    * @return
    *   the current line.
    */
  def readLine(): String = {
    try {
      val toReturn: String = line
      line = in.readLine()
      toReturn
    } catch {
      case e: IOException => throw new RuntimeException(e)
    }
  }

  /** Closes the underlying file reader.
    */
  def close(): Unit = {
    try {
      in.close()
    } catch {
      case e: IOException => throw new RuntimeException(e)
    }
  }
}
