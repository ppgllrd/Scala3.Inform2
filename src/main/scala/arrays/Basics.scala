/*
  Pepe Gallardo, 2022
 */

package arrays

@main def basicsTest(): Unit =

  val xs = new Array[Double](100)
  for(i <- 0 until xs.length)
    xs(i) = math.sqrt(i.toDouble)

  val ys = Array[Int](2, 3, 5, 7, 11, 13)

  val zs1 = Array.fill[Int](10)(1) // ten ones
  val zs2 = Array.range(0, 10) // from 0 to 9
  val zs3 = Array.tabulate[Int](10)(i => i*i)  // from 0 to 81
  val zs4 = Array.iterate[Int](1, 10)(x => 2*x) // from 1 to 512 (10 elements)

  def printArray[A](xs: Array[A]): Unit =
    val l = xs.length
    print("Array(")

    for i <-0 until l-1 do
      print(s"${xs(i)}, ") // print all elements but last one followed by a comma

    if l > 0 then
      print(xs(l-1)) // print last element

    print(")")


  def maximum[A](xs: Array[A])(using ord: Ordering[A]): A =
    import ord.* // use order relationship for type A

    var max = xs(0)
    for i <- 1 until xs.length do
      if xs(i) > max then
        max = xs(i)
    max

  printArray(zs2)
  println
  println(zs2.mkString("Array(", ", ", ")"))
  println(maximum(zs2))
