/*
  Pepe Gallardo, 2022
 */

package classes.mandelbrot

import scala.math.*

trait Complex:
  def re: Double  // real part
  def im: Double  // imaginary part
  def mod: Double // modulus
  def arg: Double // argument

  def +(that: Complex): Complex =
    Cartesian(re + that.re, im + that.im)

  def -(that: Complex): Complex =
    Cartesian(re - that.re, im - that.im)

  def *(that: Complex): Complex =
    Polar(mod * that.mod, arg + that.arg)

  def /(that: Complex): Complex =
    Polar(mod / that.mod, arg / that.arg)


case class Cartesian(re: Double, im: Double) extends Complex:
  def mod: Double = sqrt(pow(re, 2) + pow(im, 2))
  def arg: Double = if re == 0 && im == 0 then 0 else atan2(im, re)

  override def toString: String = s"Cartesian($re, $im)"


case class Polar(mod: Double, arg: Double) extends Complex:
  def re: Double = mod * cos(arg)
  def im: Double = mod * sin(arg)

  override def toString: String = s"Polar($mod, $arg)"


@main def complexTest(): Unit =
  val c1: Complex = Cartesian(1, 1)
  val c2: Complex = Polar(1, Pi/2)
  val c3: Complex = c1 + c2
  println(c1)
  println(c2)
  println(c3)

