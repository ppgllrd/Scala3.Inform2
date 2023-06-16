/*
  Pepe Gallardo, 2023
 */

package files

import java.nio.file.{Path, Files, StandardOpenOption}

object MultiplicationTable:
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
