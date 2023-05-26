/******************************************************************************
 * Informática. Grado en Matemáticas. Universidad de Málaga
 * @ Pepe Gallardo
 *
 * Robot
 *
 *****************************************************************************/

package inform.robot

import java.io._

private class MapReader(fileName: String) {
  private var in: BufferedReader = null
  private var line: String = null

  try {
    in = new BufferedReader(new java.io.FileReader(fileName))
    line = in.readLine()
  } catch {
    case e: IOException => throw new RuntimeException(e)
  }

  def hasMoreLines: Boolean = line != null

  def readLine(): String = {
      try {
        val toReturn: String = line
        line = in.readLine()
        toReturn
      } catch {
        case e : IOException => throw new RuntimeException(e)
      }
    }

  def close(): Unit = {
    try {
      in.close()
    } catch {
      case e: IOException => throw new RuntimeException(e)
    }
  }
}