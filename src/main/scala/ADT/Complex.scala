/*
  Pepe Gallardo, 2023
 */

package ADT

import scala.math.*

/**
 * Represents a complex number using an ADT with two representations.
 *
 * Each case stores its native components and computes the others on demand.
 * For example, `Cartesian` stores `re` and `im` and derives `mod` and `arg`.
 */
enum Complex:
  /** Cartesian (rectangular) form: real part `re` and imaginary part `im`. */
  case Cartesian(override val re: Double, override val im: Double)

  /** Polar form: modulus `mod` and argument (angle in radians) `arg`. */
  case Polar(override val mod: Double, override val arg: Double)

  // Default implementations derive the missing representation from the stored one.
  def mod: Double = sqrt(pow(re, 2) + pow(im, 2))
  def arg: Double = if re == 0 && im == 0 then 0 else atan2(im, re)
  def re: Double = mod * cos(arg)
  def im: Double = mod * sin(arg)

  /** Addition — performed in Cartesian form. */
  def +(that: Complex): Complex =
    Cartesian(re + that.re, im + that.im)

  /** Subtraction — performed in Cartesian form. */
  def -(that: Complex): Complex =
    Cartesian(re - that.re, im - that.im)

  /** Multiplication — performed in Polar form (multiply moduli, add arguments). */
  def *(that: Complex): Complex =
    Polar(mod * that.mod, arg + that.arg)

  /** Division — performed in Polar form (divide moduli, subtract arguments). */
  def /(that: Complex): Complex =
    Polar(mod / that.mod, arg / that.arg)

  /** Approximate equality: two complex numbers are equal if their real and imaginary
   *  parts are within a small epsilon, allowing comparison across representations.
   */
  override def equals(obj: Any): Boolean =
    // tolerance-based comparison to handle floating-point imprecision
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
