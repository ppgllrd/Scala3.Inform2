/*
  Pepe Gallardo, 2023
 */

// Demonstrates Scala tuples: creation, component access via _1/_2, pattern binding, and pattern matching.

package tuples

@main def tuples(): Unit =
  val t1: (Int, Char) = (10, 'x') // a 2-component tuple

  // Access tuple components using _1 and _2 accessors
  def printComponents[A, B](t: (A, B)): Unit =
    val c1 = t._1
    val c2 = t._2
    println(s"First component is $c1 and second component is $c2")

  printComponents(t1)
  printComponents((false, 20))

  // Destructure a tuple using pattern binding (val declaration)
  def printComponents2[A, B](t: (A, B)): Unit =
    val (c1, c2) = t // pattern binding
    println(s"First component is $c1 and second component is $c2")

  printComponents2(t1)

  // Destructure a tuple using pattern matching (match expression)
  def printComponents3[A, B](t: (A, B)): Unit = t match
    case (c1, c2) => // pattern matching
      println(s"First component is $c1 and second component is $c2")

  printComponents3(t1)

  val t2: (Int, Boolean, Char) = (123, false, 'z') // a 3-component tuple

  val t3: (Int, Boolean, Int) = (123, false, 25)
