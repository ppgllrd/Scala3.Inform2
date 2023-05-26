package objects

object John {
  val name: String = "John"

  val surname: String = "Smith"

  var age: Int = 18

  var height: Double = 170

  def getsOlder(): Unit =
    age += 1

  def getsTaller(delta: Double): Unit =
    height += delta

  override def toString: String =
    s"John($name, $surname, $age, $height)"
}


@main def objectsTest(): Unit =
  println(John.toString)

  John.getsOlder() // gets 1 year older
  John.getsTaller(5) // grows 5 centimeters

  println(John) // same as println(John.toString)


