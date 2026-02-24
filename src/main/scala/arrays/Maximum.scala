/*
  Pepe Gallardo, 2023
 */

package arrays

/** Demonstrates finding the maximum element in a generic array using Ordering. */
object Maximum:
  /**
   * Returns the maximum element in a non-empty array.
   * Works with any type A that has an Ordering (Int, String, etc.)
   * thanks to the context parameter `ord`.
   */
  def maximum[A](xs: Array[A])(using ord: Ordering[A]): A =
    import ord.* // brings comparison operators (<, >, <=, >=) into scope
    require(xs.nonEmpty, "Array cannot be empty")

    var max = xs(0) // start with first element as candidate
    for i <- 1 until xs.length do if xs(i) > max then max = xs(i)
    max

@main def testMaximum(): Unit =
  val xs = Array(1, 7, 12, 9, 3, 0)
  val x = Maximum.maximum(xs)
  println(s"Maximum element in array ${xs.mkString("Array(", ", ", ")")} is $x")

  val ys = Array("hi", "there", "my", "friend")
  val y = Maximum.maximum(ys)
  println(s"Maximum element in array ${ys.mkString("Array(", ", ", ")")} is $y")
