/** **************************************************************************************
  * Informática. Grado en Matemáticas. Universidad de Málaga. \@ Pepe Gallardo
  *
  * Random values generator
  */

package inform.util

object random:
  export scala.util.Random

  extension (rnd: scala.util.Random)
    /** @return
      *   A random Int value. Uniform distribution.
      */
    inline def int(): Int =
      rnd.nextInt()

    /** @param n
      *   exclusive upper limit of interval to choose from.
      * @return
      *   A random Int in [0, n) range. Uniform distribution.
      */
    inline def int(n: Int): Int =
      rnd.nextInt(n)

    /** @param min
      *   inclusive lower limit of interval to choose from.
      * @param max
      *   exclusive upper limit of interval to choose from.
      * @return
      *   A random Int in [min, max) range. Uniform distribution.
      */
    inline def int(min: Int, max: Int): Int =
      rnd.between(min, max)

    /** @return
      *   A random Long value. Uniform distribution.
      */
    inline def long(): Long =
      rnd.nextLong()

    /** @param n
      *   exclusive upper limit of interval to choose from.
      * @return
      *   A random Long in [0, n) range. Uniform distribution.
      */
    inline def long(n: Long): Long =
      rnd.nextLong(n)

    /** @param min
      *   inclusive lower limit of interval to choose from.
      * @param max
      *   exclusive upper limit of interval to choose from.
      * @return
      *   A random Long in [min, max) range. Uniform distribution.
      */
    inline def long(min: Long, max: Long): Long =
      rnd.between(min, max)

    /** @param l
      *   length of random string.
      * @return
      *   A random string with length l. Uniform distribution among all possible characters.
      */
    inline def string(l: Int): String =
      rnd.nextString(l)

    /** @return
      *   A random Boolean value (false or true). Uniform distribution (probability 1/2 for each
      *   value).
      */
    inline def boolean(): Boolean =
      rnd.nextBoolean()

    /** @return
      *   A random Double value between 0.0 and 1.0. Uniform distribution.
      */
    inline def double(): Double =
      rnd.nextDouble()

    /** @param min
      *   lower limit of interval to choose from.
      * @param max
      *   upper limit of interval to choose from.
      * @return
      *   A random Double between min and max. Uniform distribution.
      */
    inline def double(min: Double, max: Double): Double =
      rnd.between(min, max)

    /** @param n
      *   upper limit of interval to choose from.
      * @return
      *   A random Double value between 0.0 and n. Uniform distribution.
      */
    def double(n: Double): Double =
      if n <= 0 then throw new NoSuchElementException("double: n must be greater than 0.0")

      val next = rnd.nextDouble() * n
      if next < n then next
      else Math.nextAfter(n, Double.NegativeInfinity)

    /** @return
      *   A random Float value between 0.0 and 1.0. Uniform distribution.
      */
    inline def float(): Float =
      rnd.nextFloat()

    /** @param min
      *   lower limit of interval to choose from.
      * @param max
      *   upper limit of interval to choose from.
      * @return
      *   A random Float between min and max. Uniform distribution.
      */
    inline def float(min: Float, max: Float): Float =
      rnd.between(min, max)

    /** @param n
      *   upper limit of interval to choose from.
      * @return
      *   A random Float value between 0.0 and n. Uniform distribution.
      */
    def float(n: Float): Float =
      if n <= 0 then throw new NoSuchElementException("float: n must be greater than 0.0")

      val next = rnd.nextFloat() * n
      if next < n then next
      else Math.nextAfter(n, Float.NegativeInfinity)

    /** @param seq
      *   sequence of elements to choose from.
      * @return
      *   A random element taken from sequence seq. Uniform distribution among all elements in seq.
      */
    def fromSeq[A](seq: collection.Seq[A]): A =
      seq(rnd.nextInt(seq.length))

    /** @param seq
      *   sequence of elements to choose from.
      * @return
      *   A random element taken from sequence seq. Uniform distribution among all elements in seq.
      */
    inline def oneOf[A](seq: collection.Seq[A]): A =
      rnd.fromSeq(seq)

    /** @param iterable
      *   iterable of elements to choose from.
      * @return
      *   A random element taken from iterable. Uniform distribution among all elements in iterable.
      */
    def fromIterable[A](iterable: collection.Iterable[A]): A =
      val it = iterable.iterator

      val skip = rnd.nextInt(iterable.size)
      for _ <- 0 until skip do it.next()

      it.next()

    /** @param set
      *   set of elements to choose from.
      * @return
      *   A random element taken from set. Uniform distribution among all elements in set.
      */
    inline def fromSet[A](set: collection.Set[A]): A =
      rnd.fromIterable(set)

    /** @param set
      *   set of elements to choose from.
      * @return
      *   A random element taken from set. Uniform distribution among all elements in set.
      */
    inline def oneOf[A](set: collection.Set[A]): A =
      rnd.fromSet(set)

    /** @return
      *   A random Double value between 0.0 and 1.0. Uniform distribution.
      */
    inline def uniform(): Double = rnd.nextDouble()

    /** @param n
      *   exclusive upper limit of interval to choose from.
      * @return
      *   A random Int value in [0, n) range. Uniform distribution.
      */
    inline def uniform(n: Int): Int =
      rnd.nextInt(n)

    /** @param min
      *   inclusive lower limit of interval to choose from.
      * @param max
      *   exclusive upper limit of interval to choose from.
      * @return
      *   A random Int in [min, max) range. Uniform distribution.
      */
    inline def uniform(min: Int, max: Int): Int =
      rnd.between(min, max)

    /** @param min
      *   inclusive lower limit of interval to choose from.
      * @param max
      *   exclusive upper limit of interval to choose from.
      * @return
      *   A random Long in [min, max) range. Uniform distribution.
      */
    inline def uniform(min: Long, max: Long): Long =
      rnd.between(min, max)

    /** @param min
      *   lower limit of interval to choose from.
      * @param max
      *   upper limit of interval to choose from.
      * @return
      *   A random Double value between min and max. Uniform distribution.
      */
    inline def uniform(min: Double, max: Double): Double =
      rnd.between(min, max)

    /** @param min
      *   lower limit of interval to choose from.
      * @param max
      *   upper limit of interval to choose from.
      * @return
      *   A random Float value between min and max. Uniform distribution.
      */
    inline def uniform(min: Float, max: Float): Float =
      rnd.between(min, max)

    /** @return
      *   A random Boolean value. Bernoulli distribution with success (true) probability of 1/2.
      */
    def bernoulli(): Boolean =
      rnd.nextDouble() < 0.5

    /** @param p
      *   success probability.
      * @return
      *   A random Boolean value. Bernoulli distribution with success probability of p.
      */
    def bernoulli(p: Double): Boolean =
      if p < 0.0 || p > 1.0 then
        throw new IllegalArgumentException(
          "bernoulli: success probability must be in 0.0 to 1.0 range"
        )
      else rnd.nextDouble() < p

    /** @param p
      *   success probability.
      * @return
      *   A random Int value. Geometric (with success probability of p) distribution.
      */
    def geometric(p: Double): Int =
      if p < 0.0 || p > 1.0 then
        throw new IllegalArgumentException(
          "geometric: success probability must be in 0.0 to 1.0 range"
        )
      else
        // using algorithm given by Knuth
        scala.math.ceil(scala.math.log(rnd.nextDouble()) / scala.math.log(1.0 - p)).toInt

    /** @return
      *   Random Double value. Normal distribution (mean 0.0 and standard deviation 1.0).
      */
    inline def normal(): Double = rnd.nextGaussian()

    /** @param mu
      *   mean.
      * @param sigma
      *   standard deviation.
      * @return
      *   Random Double value. Normal distribution (mean is mu and standard deviation is sigma).
      */
    def normal(mu: Double = 0, sigma: Double = 1): Double =
      mu + sigma * rnd.nextGaussian()

    /** @return
      *   Random Double value. Lognormal distribution (mean 0.0 and standard deviation 1.0).
      */
    def lognormal(): Double = scala.math.exp(normal())

    /** @param mu
      *   mean.
      * @param sigma
      *   standard deviation.
      * @return
      *   Random Double value. Lognormal distribution (mean is mu and standard deviation is sigma).
      */
    def lognormal(mu: Double = 0, sigma: Double = 1): Double =
      scala.math.exp(normal(mu, sigma))

    /** @param lambda
      *   rate parameter.
      * @return
      *   Random Double value. Exponential distribution (lambda is rate parameter).
      */
    def exp(lambda: Double): Double =
      if lambda <= 0.0 then throw new IllegalArgumentException("exp: lambda must be greater than 0")
      else -scala.math.log(1.0 - rnd.nextDouble()) / lambda

    /** @param alpha
      *   scale.
      * @param beta
      *   shape.
      * @return
      *   Random Double value. Weibull distribution (alpha is scale and beta is shape).
      */
    def weibull(alpha: Double, beta: Double): Double =
      alpha * scala.math.pow(-scala.math.log(1.0 - rnd.nextDouble()), 1.0 / beta)

    /** @param lambda
      *   rate paraemter.
      * @return
      *   Random Double value. Poisson distribution (lambda is rate parameter).
      */
    def poisson(lambda: Double): Int =
      if lambda <= 0.0 then
        throw new IllegalArgumentException("poisson: lambda must be greater than 0")
      if lambda.isInfinite then throw new IllegalArgumentException("poisson: lambda must be finite")
      // using algorithm given by Knuth
      // see http://en.wikipedia.org/wiki/Poisson_distribution
      var k: Int = 0
      var p = 1.0
      val L = scala.math.exp(-lambda)
      var end = false
      while !end do
        k += 1
        p *= rnd.nextDouble()
        end = p < L

      k - 1

    /** @return
      *   Random Double value. Cauchy distribution.
      */
    def cauchy(): Double =
      scala.math.tan(scala.math.Pi * (rnd.nextDouble() - 0.5))

    /** @param alpha
      *   shape parameter
      * @return
      *   Random Double value from Pareto distribution.
      */
    def pareto(alpha: Double): Double =
      if alpha <= 0.0 then
        throw new IllegalArgumentException("pareto: alpha must be greater than 0")
      else scala.math.pow(1 - rnd.nextDouble(), -1.0 / alpha) - 1.0

    /** @param seq
      *   sequence of elements to choose from.
      * @return
      *   A random element in sequence seq. Uniform distribution among all elements in seq.
      */
    def discrete[A](seq: Seq[A]): A = seq(rnd.nextInt(seq.length))

    /** Constructs an impure function returning random Ints from 0 to frequencies.length - 1.
      *
      * @param frequencies
      *   array defining frequencies for each possible random outcome.
      * @return
      *   An impure function returning random Ints from 0 to frequencies.length - 1. Probability of
      *   value i is proportional to frequencies(i).
      */
    def discrete(frequencies: Array[Int]): () => Int =
      discrete(frequencies.toIndexedSeq)

    /** Constructs an impure function returning random Ints from 0 to frequencies.length - 1.
      *
      * @param frequencies
      *   sequence defining frequencies for each possible random outcome.
      * @return
      *   An impure function returning random Ints from 0 to frequencies.length - 1. Probability of
      *   value i is proportional to frequencies(i).
      */
    def discrete(frequencies: Seq[Int]): () => Int =
      var sum = 0
      for f <- frequencies do
        if f < 0 then
          throw new IllegalArgumentException(
            s"All frequencies must be positive values but found $f."
          )
        sum += f

      if sum == 0 then
        throw new IllegalArgumentException("At least one frequency must be greater than 0")
      if sum >= Integer.MAX_VALUE then
        throw new IllegalArgumentException("Sum of frequencies is too large")

      val accumulators = new Array[Int](frequencies.length)
      sum = 0
      for i <- accumulators.indices do
        sum += frequencies(i)
        accumulators(i) = sum

      def searcher(): Int =
        val n = rnd.nextInt(sum)
        var found = false
        var i = 0
        while !found && i < accumulators.length do
          if accumulators(i) > n then found = true
          else i += 1
        if found then i else -1

      searcher

    /** Randomly shuffles contents of Sequence seq.
      * @param seq
      *   sequence of elements to shuffle.
      */
    def shuffleInPlace[A](seq: scala.collection.mutable.Seq[A]): Unit =
      val l = seq.length
      for i <- 0 until l do
        val j = i + rnd.nextInt(l - i)
        val tmp = seq(i)
        seq(i) = seq(j)
        seq(j) = tmp