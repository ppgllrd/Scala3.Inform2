package inform.statistics

import scala.annotation.tailrec
import scala.language.postfixOps

/**
  * Created by pepeg on 14/02/2016.
  */
package object basic {
  import scala.math._

  def mean[D](xs : Array[D])(implicit ev: D => Double) : Double = {
    var s = 0.0
    for(i <- 0 until xs.length)
      s += ev(xs(i))
    s / xs.length
  }
  def average[D](xs : Array[D])(implicit ev: D => Double) : Double = mean(xs)

  def meanAndStandardDeviation[D](xs : Array[D])(implicit ev: D => Double) : (Double,  Double) = {
    val m = mean(xs)
    var s = 0.0
    for(x <- xs)
      s += pow(ev(x)-m,2)
    val dev = sqrt(s/xs.length)
    return (m, dev)
  }

  def standardDeviation[D](xs : Array[D])(implicit ev: D => Double) : Double = meanAndStandardDeviation(xs)._2

  def median[D](xs : Array[D])(implicit ev: D => Double) : Double = {
    val copy : Array[Double] = xs.map(ev(_))
    findMedianInPlace(copy)(_.apply(0))
  }

  def statistics[D](xs : Array[D])(implicit ord : Ordering[D], ev: D => Double) : (Double,  Double, Double, Double, Double) = {
    val ys = xs.map(ev(_))
    val (m,d) = meanAndStandardDeviation(ys)
    (m, d, median(xs), ys.min, ys.max)
  }



  // This code is from: http://stackoverflow.com/questions/4662292/scala-median-implementation
  protected case class ArrayView(arr: Array[Double], from: Int, until: Int) {
    def apply(n: Int): Double =
      if (from + n < until) arr(from + n)
      else throw new ArrayIndexOutOfBoundsException(n)

    def partitionInPlace(p: Double => Boolean): (ArrayView, ArrayView) = {
      var upper = until - 1
      var lower = from
      while (lower < upper) {
        while (lower < until && p(arr(lower))) lower += 1
        while (upper >= from && !p(arr(upper))) upper -= 1
        if (lower < upper) { val tmp = arr(lower); arr(lower) = arr(upper); arr(upper) = tmp }
      }
      (copy(until = lower), copy(from = lower))
    }

    def size: Int = until - from
    def isEmpty: Boolean = size <= 0

    override def toString: String = arr mkString ("ArraySize(", ", ", ")")
  }
  
  object ArrayView {
    def apply(arr: Array[Double]) = new ArrayView(arr, 0, arr.length)
  }

  @tailrec private def findKMedianInPlace(arr: ArrayView, k: Int)(implicit choosePivot: ArrayView => Double): Double = {
    val a = choosePivot(arr)
    val (s, b) = arr partitionInPlace (a >)
    if (s.size == k) a
    // The following test is used to avoid infinite repetition
    else if (s.isEmpty) {
      val (s, b) = arr partitionInPlace (a ==)
      if (s.size > k) a
      else findKMedianInPlace(b, k - s.size)
    } else if (s.size < k) findKMedianInPlace(b, k - s.size)
    else findKMedianInPlace(s, k)
  }

  private def findMedianInPlace(arr: Array[Double])(implicit choosePivot: ArrayView => Double) =
    findKMedianInPlace(ArrayView(arr), (arr.length - 1) / 2)
}
