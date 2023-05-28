/*
  Pepe Gallardo, 2023
 */

package ADT

import scala.math.*

enum Complex:
  case Cartesian(override val re: Double, override val im: Double)

  case Polar(override val mod: Double, override val arg: Double)

  def mod: Double = sqrt(pow(re, 2) + pow(im, 2))
  def arg: Double = if re == 0 && im == 0 then 0 else atan2(im, re)
  def re: Double = mod * cos(arg)
  def im: Double = mod * sin(arg)

  def +(that: Complex): Complex =
    Cartesian(re + that.re, im + that.im)

  def -(that: Complex): Complex =
    Cartesian(re - that.re, im - that.im)

  def *(that: Complex): Complex =
    Polar(mod * that.mod, arg + that.arg)

  def /(that: Complex): Complex =
    Polar(mod / that.mod, arg / that.arg)

  override def equals(obj: Any): Boolean =
    def eq(x: Double, y: Double): Boolean =
      val epsilon = 0.0001
      (x - y).abs < epsilon

    obj match
      case that: Complex => eq(re, that.re) && eq(im, that.im)
      case n: Double     => eq(re, n) && eq(im, 0)
      case _             => false

@main def complexTest(): Unit =
  val c1: Complex = Complex.Cartesian(1, 1)
  val c2: Complex = Complex.Polar(1, Pi / 2)
  val c3: Complex = c1 + c2

  println(c1)
  println(c2)
  println(c3)

  println(c2 == Complex.Cartesian(0, 1))
