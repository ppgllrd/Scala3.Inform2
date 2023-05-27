/*
  Pepe Gallardo, 2023
 */

package ADT


object Temperature:
  private def fahrenheitToCelsius(f: Double): Double =
    (f - 32) * 5 / 9

  private def celsiusToFahrenheit(c: Double): Double =
    c * 9 / 5 + 32

enum Temperature:
  import Temperature.*

  case Celsius(c: Double)
  case Fahrenheit(f: Double)

  def isFrozen: Boolean = this match
    case Celsius(c) => c <= 0
    case Fahrenheit(f) => f <= 32

  def toCelsius: Temperature = this match
    case Celsius(c) => this
    case Fahrenheit(f) => Celsius(fahrenheitToCelsius(f))

  def toFahrenheit: Temperature = this match
    case Celsius(c) => Fahrenheit(celsiusToFahrenheit(c))
    case Fahrenheit(f) => this

  override def equals(obj: Any): Boolean = this match
      case Celsius(c1) => obj match
        case Celsius(c2) => c1 == c2
        case Fahrenheit(f) => c1 == fahrenheitToCelsius(f) // beware of floating point errors
        case _ => false
      case Fahrenheit(f1) => obj match
        case Celsius(c) => f1 == celsiusToFahrenheit(c)
        case Fahrenheit(f2) => f1 == f2
        case _ => false


@main def testTemperatures(): Unit =
  val t1: Temperature = Temperature.Celsius(0)
  val t2: Temperature = Temperature.Fahrenheit(32)
  val t3: Temperature = Temperature.Fahrenheit(60)

  println(t1 == t2)

  val ts: List[Temperature] = List(t1, t2, t3)
  for t <- ts do
    println(s"Water is frozen at $t: ${t.isFrozen} ")