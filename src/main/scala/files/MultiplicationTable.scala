/*
  Pepe Gallardo, 2023
 */

package files

import java.io.{File, PrintStream}

object MultiplicationTable:
  def writeToFile(fileName: String, n: Int): Unit =
    require(n > 0, "number must be larger than 0")

    val file = File(fileName)
    val ps = PrintStream(file)

    for i <- 1 to 10 do ps.println(f"$n%d x $i%2d = ${n * i}%2d")
    ps.close()

@main def testMultiplicationTable(): Unit =
  MultiplicationTable.writeToFile("data/files/table7.txt", 7)
