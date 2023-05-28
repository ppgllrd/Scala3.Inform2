/** **************************************************************************************
  * Informática. Grado en Matemáticas. Universidad de Málaga. \@ Pepe Gallardo.
  *
  * Basic descriptive statistics
  */

package inform.statistics

import scala.annotation.tailrec
import scala.language.postfixOps
import scala.math.*

object descriptive:
  def mean[D](xs: Array[D])(using ev: D => Double): Double =
    var s = 0.0
    for x <- xs do s += ev(x)
    s / xs.length

  def average[D](xs: Array[D])(using ev: D => Double): Double =
    mean(xs)

  def meanAndStandardDeviation[D](xs: Array[D])(using ev: D => Double): (Double, Double) =
    val m = mean(xs)
    var s = 0.0
    for x <- xs do s += pow(ev(x) - m, 2)
    val dev = sqrt(s / xs.length)
    (m, dev)

  def standardDeviation[D](xs: Array[D])(using ev: D => Double): Double =
    meanAndStandardDeviation(xs)._2

  def median[D](xs: Array[D])(using ev: D => Double): Double =
    val copy: Array[Double] = xs.map(ev)
    findMedianInPlace(copy)((xs: ArrayView) => xs(0))

  def statistics[D](
      xs: Array[D]
  )(using ord: Ordering[D], ev: D => Double): (Double, Double, Double, Double, Double) =
    val ys = xs.map(ev)
    val (m, d) = meanAndStandardDeviation(ys)
    (m, d, median(xs), ys.min, ys.max)

  // This code is from: http://stackoverflow.com/questions/4662292/scala-median-implementation
  protected case class ArrayView(arr: Array[Double], from: Int, until: Int):
    def apply(n: Int): Double =
      if from + n < until then arr(from + n)
      else throw new ArrayIndexOutOfBoundsException(n)

    def partitionInPlace(p: Double => Boolean): (ArrayView, ArrayView) =
      var upper = until - 1
      var lower = from
      while lower < upper do
        while lower < until && p(arr(lower)) do lower += 1
        while upper >= from && !p(arr(upper)) do upper -= 1
        if lower < upper then { val tmp = arr(lower); arr(lower) = arr(upper); arr(upper) = tmp }

      (copy(until = lower), copy(from = lower))

    def size: Int = until - from

    def isEmpty: Boolean = size <= 0

    override def toString: String = arr mkString ("ArrayView(", ", ", ")")

  protected object ArrayView:
    def apply(arr: Array[Double]) = new ArrayView(arr, 0, arr.length)

  @tailrec private def findKMedianInPlace(arr: ArrayView, k: Int)(
      choosePivot: ArrayView => Double
  ): Double =
    val a = choosePivot(arr)
    val (s, b) = arr partitionInPlace (a >)
    if s.size == k then a
    // The following test is used to avoid infinite repetition
    else if s.isEmpty then
      val (s, b) = arr partitionInPlace (a ==)
      if s.size > k then a
      else findKMedianInPlace(b, k - s.size)(choosePivot)
    else if s.size < k then findKMedianInPlace(b, k - s.size)(choosePivot)
    else findKMedianInPlace(s, k)(choosePivot)

  private def findMedianInPlace(arr: Array[Double])(choosePivot: ArrayView => Double) =
    findKMedianInPlace(ArrayView(arr), (arr.length - 1) / 2)(choosePivot)
