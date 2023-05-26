package syntax

import scala.annotation.tailrec

@main def vals(): Unit =
  val x: Int = 10

  val y: Double = 2.25

  val flag: Boolean = true

  val z = 123 // type inference

  val c: Char = 'a'

  val s: String = "hello"


@main def expressions(): Unit =
  val n1: Int = 10 * 2
  val n2: Int = n1 + 5
  val n3: Int = (10 + 2) * 5

  val b1: Boolean = true
  val b2: Boolean = !b1 || (n1 % 2 == 0)
  val b3: Boolean = (n2 % 2 == 0) && (n2 > 5)

  val s1: String = "hi"
  val s2: String = "there"
  val s3: String = s1 + s2

  import scala.math.*

  val d1: Double = 2 * Pi
  val d2: Double = cos(d1) / 3



@main def blocks(): Unit =
  val x: Int =
    // a block for defining x
    val y = 5 + 2
    val z = y * y
    z * 100

  val y: Int = 1000

  val z: Int =
    // a block for defining z
    val n = 3 * 2
    n + y


@main def vars(): Unit =
  var x: Int = 10
  var y: Int = x
  println(s"x=$x y=$y")

  x = 20
  println(s"x=$x y=$y")

  x = x + 1
  println(s"x=$x y=$y")

  x += 1
  println(s"x=$x y=$y")

  y *= 3
  println(s"x=$x y=$y")


@main def conditionals(): Unit =
  import scala.util.Random

  val seed = 0
  val rnd = Random(seed) // random generator

  val n: Int = rnd.between(0, 100)

  if n % 2 == 0 then
    println(s"$n is an even number")
  else
    println(s"$n is an odd number")

  val mark: Double = rnd.between(0.0, 10.0)
  println(s"Mark is $mark")

  if mark < 5.0 then
    println("Low")
  else if mark < 8.0 then
    println("Nice")
  else if mark < 9.0 then
    println("Good")
  else
    println("Very good")


@main def functions(): Unit =
  def square(x: Int): Int = x * x

  def pythagoras(x: Int, y: Int): Int = square(x) + square(y)

  println(square(10))
  println(pythagoras(10, 20))

  // recursion
  def factorial(n: Long): Long =
    if n == 0 then
      1
    else if n > 0 then
      n * factorial(n - 1)
    else
      sys.error(s"$n shouldn't be a negative number")

  println(factorial(5))


  // local functions
  def areaCylinder(r: Double, h: Double): Double =
    import scala.math.*

    def areaCircle(r: Double): Double =
      Pi * pow(r, 2)

    def lengthCircle(r: Double): Double =
      2 * Pi * r

    def areaRectangle(b: Double, h: Double): Double =
      b * h

    val len = lengthCircle(r)
    val rect = areaRectangle(len, h)
    val circ = areaCircle(r)

    2 * circ + rect


  // recursion and accumulator parameters
  def factorialAc(n: Long): Long =
    // aux is a local function
    @tailrec
    def aux(i: Long, ac: Long): Long =
      if i == 0 then
        ac
      else
        aux(i - 1, i * ac)

    require(n >= 0, s"$n shouldn't be a negative number")
    aux(n, 1)

  println(factorialAc(5))



  // higher order functions
  def twice(f: Int => Int, x: Int): Double =
    f(f(x))

  println(twice(square, 10))
  println(twice(x => 2 * x, 10))

  // generics and higher order functions
  def twice2[A](f: A => A, x: A): A =
    f(f(x))

  println(twice2[Int](square, 10))
  println(twice2(square, 10)) // type inference

  println(twice2[Double](scala.math.cos, scala.math.Pi))
  println(twice2(scala.math.cos, scala.math.Pi)) // type inference

  // higher order functions and function composition
  def compose[A, B, C](f: B => C, g: A => B): A => C =
    x => f(g(x))

  val odd: Int => Boolean =
    compose[Int, Boolean, Boolean](x => !x, x => x % 2 == 0)
  println(odd(10))

  val odd2: Int => Boolean =
    compose((x: Boolean) => !x, (x:Int) => x % 2 == 0)
  println(odd2(10))


  def not(x: Boolean): Boolean = !x
  def even(x: Int): Boolean = x % 2 == 0
  val odd3: Int => Boolean = compose(not, even)
  println(odd3(11))


@main def curriedFunctions(): Unit =
  def f(x: Int)(y: Int): Int = x*x + y*y

  val v: Int = f(10)(20) // if we apply both arguments we get an Int
  println(s"v is $v")

  val g: Int => Int = f(10) // if we apply just onr argument we get a function
  val z: Int = g(20)
  println(s"z is $z")


@main def whileLoop(): Unit =
  val upper = 10

  var sum = 0
  var i = 0
  while i <= upper do
    sum += i
    i += 1

  println(s"Sum from 0 to $upper is $sum")


@main def forLoop(): Unit =
  val upper = 10

  var sum = 0
  for i <- 0 to upper do
    sum += i

  println(s"Sum from 0 to $upper is $sum")


  for i <- 0 until upper do
    println(i)

  println

  for i <- 0 until upper by 2 do
    println(i)


@main def procedures(): Unit =
  def printMultiplicationTable(n: Int): Unit =
    require(n > 0, "number cannot be negative")

    for i <- 1 to 10 do
      val p = n * i
      println(s"$n x $i = $p")

  printMultiplicationTable(3)
