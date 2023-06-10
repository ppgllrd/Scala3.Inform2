/*
  Pepe Gallardo, 2023
 */

package random

@main def randomTest(): Unit =
  import inform.util.random.*

  val rnd = Random(0)

  val x = rnd.fromSeq(Array(10, 20, 30))
  println(x)

  val xs = Array(10, 20, 30)
  rnd.shuffleInPlace(xs)
  println(xs.mkString(" "))

  for _ <- 1 to 10 do
    val y = rnd.between(10, 20)
    println(y)

  println(rnd.int())
  println(rnd.boolean())
  val z = rnd.double(1.0, 2.0)
  println(z)
  println(rnd.float())

  println(rnd.uniform(1, 10))

  val frequencies = Array(50, 20, 30)
  val discreteGen: () => Int = rnd.discrete(frequencies)

  for _ <- 1 to 20 do
    val x = discreteGen()
    println(x)

  val set = scala.collection.immutable.Set.range(1, 10)
  println(set)

  println()
  for _ <- 1 to 10 do println(rnd.fromSet(set))
