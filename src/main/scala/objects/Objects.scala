/*
  Pepe Gallardo, 2023
 */

package objects

object John:
  // immutable attributes
  val name: String = "John"
  val surname: String = "Smith"

  // mutable attributes
  var age: Int = 18
  var height: Double = 170

  // methods
  def getsOlder(): Unit =
    age += 1

  def getsTaller(delta: Double): Unit =
    height += delta

  override def toString: String =
    s"John($name, $surname, $age, $height)"

@main def objectsTest(): Unit =
  println(John.toString)

  John.getsOlder() // gets 1 year older
  John.getsTaller(5) // grows by 5 centimeters

  println(John) // same as println(John.toString)
