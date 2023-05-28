/*
  Pepe Gallardo, 2023
 */

package arrays

object Search:
  // Left to right linear search of element x in array xs.
  // Returns Some(index) if x was firstly found at xs(index)
  // or None if x is not in xs
  def linearSearch[A](x: A, xs: Array[A]): Option[Int] =
    var opt: Option[Int] = None // element not found yet
    var i = 0 // we start at index 0 of array

    // while not found and while i is a valid index
    while opt.isEmpty && i < xs.length do
      if xs(i) == x then opt = Some(i) // element found at index i. Stops loop
      else i += 1 // not found at index i. Move to next cell in array

    opt

  def binarySearch[A](x: A, xs: Array[A])(using ord: Ordering[A]): Option[Int] =
    import ord.*
    // precondition: xs must be sorted in ascending order

    var opt: Option[Int] = None
    var left = 0
    var right = xs.length - 1

    while opt.isEmpty && left <= right do
      val mid = (left + right) / 2
      val y = xs(mid)
      if x == y then opt = Some(mid)
      else if x > y then left = mid + 1
      else right = mid - 1

    opt

@main def searchTest(): Unit =
  def printResult(opt: Option[Int]): Unit =
    println(opt match
      case Some(index) => s"Element was found at cell $index"
      case None        => s"Element was not found"
    )

  val xs = Array(7, 4, 2, 0, 5, 3, 1, 4)

  val opt1 = Search.linearSearch(0, xs)
  printResult(opt1)

  val opt2 = Search.linearSearch(4, xs)
  printResult(opt2)

  val opt3 = Search.linearSearch(9, xs)
  printResult(opt3)

  // Array must be sorted for using binary Search
  val ys = Array(0, 1, 2, 3, 4, 5, 7, 8)

  val opt4 = Search.binarySearch(0, ys)
  printResult(opt4)

  val opt5 = Search.binarySearch(4, ys)
  printResult(opt5)

  val opt6 = Search.binarySearch(9, ys)
  printResult(opt6)
