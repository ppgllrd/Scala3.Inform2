/*
  Pepe Gallardo, 2023
 */

package classes.inheritance

object Car:
  private val vat = 0.21 // value-added tax rate

/** A car with a model name and base cost. Price includes VAT. */
class Car(mod: String, cost: Double):
  def price: Double =
    cost + cost * Car.vat // cost plus taxes

  def model: String = mod

  override def toString: String =
    f"Car($mod, $cost%.2f)"

/** An imported car that adds an import tax on top of the base price. */
class ImportedCar(mod: String, cost: Double, tax: Double) extends Car(mod, cost):

  override def price: Double =
    super.price + tax // cost + vat + tax

  override def toString: String =
    f"ImportedCar($mod, $cost%.2f, $tax%.2f)"

@main def carsTest(): Unit =
  val c1 = Car("Seat Panda", 15000)
  val c2 = ImportedCar("Porsche GT3", 44000, 7000)

  val cars: List[Car] = List(c1, c2)

  for c <- cars do println(f"Total price for car ${c.model} is ${c.price}%.2f")
