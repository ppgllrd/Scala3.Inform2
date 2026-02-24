/*
  Pepe Gallardo, 2023
 */

package files

import java.nio.file.{Path, Files, StandardOpenOption}

/** Demonstrates writing formatted text to a file using two different approaches. */
object MultiplicationTable:
  /** Writes the multiplication table of `n` to a file using a `BufferedWriter`.
   *
   *  @param fileName path to the output file.
   *  @param n        the number whose multiplication table is written.
   */
  def writeToFile(fileName: String, n: Int): Unit =
    require(n > 0, "number must be larger than 0")
    val path = Path.of(fileName)
    val bw = Files.newBufferedWriter(path)

    bw.write(s"Multiplication table of $n")
    bw.newLine()
    for i <- 1 to 10 do
      bw.write(f"$n%d x $i%2d = ${n * i}%2d")
      bw.newLine()
    bw.close()

  /** Writes the multiplication table of `n` to a file using `Files.writeString`.
   *
   *  Unlike `writeToFile`, this method uses `StandardOpenOption.APPEND`
   *  to add each line to the file instead of a `BufferedWriter`.
   */
  def writeToFileWS(fileName: String, n: Int): Unit =
    require(n > 0, "number must be larger than 0")
    val path = Path.of(fileName)
    Files.writeString(path, s"Multiplication table of $n")
    Files.writeString(path, System.lineSeparator())
    for i <- 1 to 10 do
      Files.writeString(path, f"$n%d x $i%2d = ${n * i}%2d", StandardOpenOption.APPEND)
      Files.writeString(path, System.lineSeparator())

@main def testMultiplicationTable(): Unit =
  MultiplicationTable.writeToFile("data/files/table7.txt", 7)
