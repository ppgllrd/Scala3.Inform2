/*
  Pepe Gallardo, 2022
 */

package arrays

object Matrix:
  type Matrix = Array[Array[Double]]

  val matrix: Matrix =
    Array( Array( 1.0,  2.0,  3.0,  4.0)
         , Array( 5.0,  6.0,  7.0,  8.0)
         , Array( 9.0, 10.0, 11.0, 12.0)
         )

  def printMatrix(m: Matrix): Unit =
    for r <- 0 until m.length do
      for c <- 0 until m(r).length do
        val str = f"${m(r)(c)}%6.1f" // one decimal place and six characters width
        print(str)

      println


@main def matrixTest(): Unit =
  import Matrix._

  printMatrix(matrix)