package tuples

@main def tuples(): Unit =
  val t1: (Int, Char) = (10, 'x') // a 2-component tuple

  def printComponents[A,B](t: (A, B)): Unit =
    val c1 = t._1
    val c2 = t._2
    println(s"First component is $c1 and second component is $c2")

  printComponents(t1)
  printComponents((false, 20))

  def printComponents2[A, B](t: (A, B)): Unit =
    val (c1, c2) = t // pattern binding
    println(s"First component is $c1 and second component is $c2")

  printComponents2(t1)


  def printComponents3[A, B](t: (A, B)): Unit = t match
    case (c1, c2) => // pattern matching
      println(s"First component is $c1 and second component is $c2")

  printComponents3(t1)


  val t2: (Int, Boolean, Char) = (123, false, 'z')

  val t3: (Int, Boolean, Int) = (123, false, 25)
