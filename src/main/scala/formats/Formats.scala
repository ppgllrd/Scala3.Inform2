/*
  Pepe Gallardo, 2022
 */

package formats

@main def formatsTest(): Unit =
  val age = 20
  println(s"Age is $age")
  println(f"Age is $age%10d")

  val name = "John"
  println(s"Name is $name and age is $age")
  println(s"Name is $name and twice of age is ${age*2}")

  val num: Double = 3.141592653589793
  println(s"Number is $num")
  println(f"Number is $num%.3f") // three decimal places
  println(f"Number is $num%10.2f") // two decimal places and ten characters width

  // Using . instead of , as separator for decimals
  import java.util.Locale
  Locale.setDefault(Locale.ENGLISH)
  println(f"Number is $num%.3f")
