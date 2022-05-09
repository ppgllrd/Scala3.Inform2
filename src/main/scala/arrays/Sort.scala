/*
  Pepe Gallardo, 2022
 */

package arrays

object Sort:
  // checks whether array xs is sorted in ascending order
  def isSorted[A](xs: Array[A])(using ord: Ordering[A]): Boolean =
    import ord.*

    var sorted = true
    var i = 0
    while sorted && i < xs.length - 1 do
      if xs(i) <= xs(i + 1) then
        i += 1
      else
        sorted = false
    sorted

  // In place sorts an array using Selection Sort algorithm
  def selectionSort[A](xs: Array[A])(using ord: Ordering[A]): Unit =
    import ord.*

    // Swaps elements in array at indexes i and j
    def swap(i: Int, j: Int): Unit =
      val temp = xs(i)
      xs(i) = xs(j)
      xs(j) = temp

    for i <- 0 until xs.length - 1 do
      var minIndex = i
      for j <- i + 1 until xs.length do
        if xs(j) < xs(minIndex) then
          minIndex = j
      swap(i, minIndex)


  // In place sorts an array using Quick Sort algorithm
  def quickSort[A](xs: Array[A])(using ord: Ordering[A]): Unit =
    import ord.*

    // Swaps elements in array at indexes i and j
    def swap(i: Int, j: Int): Unit =
      val temp = xs(i)
      xs(i) = xs(j)
      xs(j) = temp

    // Partitions elements in fragment of array going from xs(left) to xs(right).
    // Returns final position of pivot.
    // All elements to the left of pivot will be smaller than pivot.
    // All elements to the right of pivot will be larger than or equal to the pivot.
    def partition(left: Int, right: Int): Int =
      val mid = (left + right) / 2
      val pivot = xs(mid)

      swap(mid, right) // place pivot at the end

      // Push elements smaller than the pivot to the left.
      // Invariant: all elements at the left of index p are smaller
      // than the pivot.
      var p = left
      for i <- left until right do
        if xs(i) < pivot then
          swap(p, i)
          p += 1

      swap(p, right) // finally, place pivot after smaller elements
      p

    // sorts fragment of array going from xs(left) to xs(right)
    def aux(left: Int, right: Int): Unit =
      if right > left then // if fragment has at least 2 elements
        val pivotIndex = partition(left, right)
        aux(left, pivotIndex - 1)
        aux(pivotIndex + 1, right)

    aux(0, xs.length - 1) // initial call to sort full array


@main def quickSortTest(): Unit =
  val xs = Array(6, 4, 3, 8, 1, 4, 9, 2, 5, 7)
  Sort.quickSort(xs)
  println(s"Sorted array is ${xs.mkString("Array(", ", ", ")")}")

  val ys = Array("John", "Peter", "Arthur", "Mike")
  Sort.quickSort(ys)
  println(s"Sorted array is ${ys.mkString("Array(", ", ", ")")}")


// checks whether arrays xs and ys have same elements (may be in different orders)
def sameElements[A](xs: Array[A], ys: Array[A]): Boolean =
  xs.diff(ys).isEmpty && ys.diff(xs).isEmpty


def SortCorrectness(name: String, sort: Array[Int] => Unit): Unit =
  val numTests = 1000 // number of tests to perform
  val length = 100 // length of each random array used for testing

  for seed <- 0 until numTests do
    // create an array of random integers
    val rnd = scala.util.Random(seed)
    val xs = Array.fill(length)(rnd.nextInt())

    // make a copy of the array
    val ys = xs.clone()

    // sort the copy
    sort(ys)

    // the sorted array must be in ascending order and
    // original array and sorted one should have same
    // elements (although they could be in different orders)
    val correct = Sort.isSorted(ys) && sameElements(xs, ys)

    if !correct then
      sys.error(s"$name algorithm failed")

  // if we reach this point without an error, then we passed all tests
  println(s"$name algorithm passed all sorting tests")


// tests Selection Sort with different random arrays
@main def selectionSortCorrectness(): Unit =
  SortCorrectness("Selection Sort", Sort.selectionSort)


// tests Quick Sort with different random arrays
@main def quickSortCorrectness(): Unit =
  SortCorrectness("Quick Sort", Sort.quickSort)