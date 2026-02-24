/*
  Pepe Gallardo, 2023
 */

// Demonstrates random number generation utilities: selection, shuffling, ranges, and discrete distributions.

package random

@main def randomTest(): Unit =
  import inform.util.random.*

  val rnd = Random(0)

  // Select a random element from a sequence
  val x = rnd.fromSeq(Array(10, 20, 30))
  println(x)

  // Shuffle an array in place
  val xs = Array(10, 20, 30)
  rnd.shuffleInPlace(xs)
  println(xs.mkString(" "))

  // Generate random integers in a range [10, 20)
  for _ <- 1 to 10 do
    val y = rnd.between(10, 20)
    println(y)

  // Generate random values of different primitive types
  println(rnd.int())
  println(rnd.boolean())
  val z = rnd.double(1.0, 2.0)
  println(z)
  println(rnd.float())

  // Uniform random integer in range [1, 10)
  println(rnd.uniform(1, 10))

  // Discrete distribution: element i is chosen with probability proportional to frequencies(i)
  val frequencies = Array(50, 20, 30)
  val discreteGen: () => Int = rnd.discrete(frequencies)

  for _ <- 1 to 20 do
    val x = discreteGen()
    println(x)

  // Select a random element from a Set
  val set = scala.collection.immutable.Set.range(1, 10)
  println(set)

  println()
  for _ <- 1 to 10 do println(rnd.fromSet(set))
