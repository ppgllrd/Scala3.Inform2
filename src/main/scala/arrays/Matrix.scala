/*
  Pepe Gallardo, 2023
 */

package arrays

/** Demonstrates representing and printing a 2D matrix using arrays of arrays. */
object Matrix:
  // Type alias: a Matrix is a 2D array (an array of rows, each row an array of Double)
  type Matrix = Array[Array[Double]]

  val matrix: Matrix =
    Array(Array(1.0, 2.0, 3.0, 4.0), Array(5.0, 6.0, 7.0, 8.0), Array(9.0, 10.0, 11.0, 12.0))

  /** Prints a matrix row by row with formatted columns. */
  def printMatrix(m: Matrix): Unit =
    for r <- 0 until m.length do // iterate over rows
      for c <- 0 until m(r).length do // iterate over columns in current row
        val str = f"${m(r)(c)}%6.1f" // one decimal place and six characters width
        print(str)

      println

@main def matrixTest(): Unit =
  import Matrix._

  printMatrix(matrix)
