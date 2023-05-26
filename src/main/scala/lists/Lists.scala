package lists

@main def lists1(): Unit =
  val xs = List[Int](10, 20, 30)

  println(s"Checking if xs is empty: ${xs.isEmpty}")

  // length of a list
  val l = xs.length
  println(s"The number of elements in xs is $l")

  // using head and tail
  val y = xs.head
  val ys = xs.tail
  println(s"First element is $y and rest of list is $ys")

  // using pattern binding
  val z::zs = xs // error if xs is empty
  println(s"First element is $z and rest of list is $zs")

  // processing a list using pattern matching and recursion
  def add(xs: List[Int]): Int = xs match
    case Nil => 0 // we use this case if xs is an empty list
    case y::ys => y + add(ys)  // we use this case if xs in non-empty. y is the head and ys is the tail

  // processing a list using head/tail and a loop
  def add2(xs: List[Int]): Int =
    var s = 0
    var ys = xs // ys is a variable that will represent several lists
    while ys.nonEmpty do
      s += ys.head
      ys = ys.tail
    s

// processing a list using foreach loop
def add3(xs: List[Int]): Int =
  var s = 0
  for x <- xs do
    s += x
  s


@main def lists2(): Unit =
  val xs = List[Int](10, 20, 30)

  // cons operator adds element before. cons operator is O(1)
  val ys = 5 :: xs // ys is List[Int](5, 10, 20, 30). xs has not changed

  println(s"xs is $xs")
  println(s"ys is $ys")


  // lists concatenation. ++ is O(n)
  val zs = xs ++ ys // zs is List[Int](10, 20, 30, 5, 10, 20, 30)
  println(s"xs is $xs")
  println(s"ys is $ys")
  println(s"zs is $zs")


@main def lists3(): Unit =
  val xs = List[Int](10, 21, 30)

  // higher order functions

  // map
  val ys = xs.map(x => x + 1) // ys is List[Int](11, 22, 31). xs has not changed
  println(s"xs is $xs")
  println(s"ys is $ys")


  // filter, filterNot, partition
  val zs = xs.filter(x => x % 2 == 0) // zs is List[Int](10, 30)
  val hs = xs.filterNot(x => x % 2 == 0) // hs is List[Int](21)
  val (evens, odds) = xs.partition(x => x % 2 == 0)
  println(s"zs is $zs")
  println(s"hs is $hs")
  println(s"evens is $evens")
  println(s"odds is $odds")


  // list comprehensions
  // ns is a list with even numbers in xs but squared
  val ns = for x <- xs if x % 2 == 0 yield x * x
  println(s"ns is $ns")


  // folds
  val s = xs.foldLeft(0)((x, y) => x + y)
  // s is the sum of all numbers in xs

  val p = xs.foldRight(1)((x, y) => x * y)
  // p is the product of all numbers in xs

  println(s"s is $s")
  println(s"p is $p")

  // reversing a list
  val rs = xs.foldLeft(List[Int]())((xs, x) => x :: xs)
  println(s"rs is $rs")


@main def lists4(): Unit =
  val xs = List[Int](10, 20, 30, 40, 50)

  val x = xs(2) // indexing is O(n)

  // take and drop
  val ys = xs.take(3) // ys is List[Int](10, 20, 30)

  val zs = xs.drop(2) // zs is List[Int](30, 40, 50)

  // forall and exists
  val largerThan10 = xs.forall(x => x > 10)

  val allEvens = xs.forall(x => x % 2 == 0)

  val someMultOf3 = xs.exists(x => x % 3 == 0)


  // arithmetic sequences
  val ns = List.range(1, 100) // List[Int](1, 2, 3, ..., 98, 99)

  val ms = List.range(1, 100, 2) // List[Int](1, 3, 5, ..., 97, 99)

