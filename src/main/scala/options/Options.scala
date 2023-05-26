package options

@main def options(): Unit =
  val x: Option[Int] = None

  println(x.isEmpty) // isEmpty returns true if x is a None

  val y: Option[Int] = Some(123)

  println(y.isEmpty) // isEmpty returns false if x is a Some

  var z: Option[Boolean] = None

  z = Some(true)

  val b: Boolean = z.get // returns value stored in Option. error if z is None

  // pattern matching for Option
  def patternMatch[A](opt: Option[A]): Unit = opt match
    case None => println(s"argument is a None")
    case Some(x) => println(s"argument is a Some and stores value $x")


  patternMatch(None)
  patternMatch(Some(123))
  patternMatch(Some(true))


  def multByOption(x: Int, opt: Option[Int]): Int = opt match
    case None => x
    case Some(y) => x * y


  println(multByOption(10, None))
  println(multByOption(10, Some(20)))
