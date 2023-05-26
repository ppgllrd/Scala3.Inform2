/******************************************************************************
 * Informática. Grado en Matemáticas. Universidad de Málaga
 * @ Pepe Gallardo. 
 *
 * Measuring execution times.
 *
 *****************************************************************************/

package inform.util

package object time {
  /**
   * Clocks elapsed time needed to compute f(x)
   * @param f function to evaluate
   * @param x argument for evaluating f
   * @tparam A type of argument
   * @tparam B type of result
   * @return elapsed time in seconds to compute f(x)
   */
  def elapsedTimeFor[A, B](f: A => B, x: A) : Double = {
    val t0 = System.currentTimeMillis()
    val _ = f(x)
    val t1 = System.currentTimeMillis()
    (t1 - t0) / 1000.0
  }

  /**
   * Prints elapsed times needed to compute f(x) for any x in xs
   *
   * @param f function to evaluate
   * @param xs arguments for evaluating f
   * @tparam A type of argument
   * @tparam B type of result
   */
  def elapsedTimeFor[A, B](f: A => B, xs: Iterable[A]): Unit = {
    require(xs.nonEmpty, "xs must be non-empty")
    
    val it = xs.iterator
    val x = it.next()
    var tPrev = elapsedTimeFor(f, x)
    println(f"Elapsed time for argument=$x%s: $tPrev%.2f seconds")

    while(it.hasNext) {
      val x = it.next()
      val t = elapsedTimeFor(f, x)
      println(f"Elapsed time for argument=$x%s: $t%.2f seconds. Increment factor: ${t / tPrev}%.2f")
      tPrev = t
    }
  }
}
