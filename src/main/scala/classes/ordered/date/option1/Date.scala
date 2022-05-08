/*
  Pepe Gallardo, 2022
 */

package classes.ordered.date.option1

class Date(val day: Int, val month: Int, val year: Int) extends Ordered[Date]:
  // class precondition
  require(day >= 1 && day <= Date.days(month, year) && month >= 1 && month <= 12 && year >= 0, "Invalid date")

  override def toString: String =
    s"Date($day, $month, $year)"

  override def equals(obj: Any): Boolean = obj match
    case that: Date => this.day == that.day && this.month == that.month && this.year == that.year
    case _ => false

  override def hashCode(): Int =
    var code = 1
    code = 31 * code + day.##
    code = 31 * code + month.##
    code = 31 * code + year.##
    code

  override def compare(that: Date): Int =
    var cmp = this.year.compare(that.year)
    if cmp == 0 then
      cmp = this.month.compare(that.month)
      if cmp == 0 then
        cmp = this.day.compare(that.day)
    cmp


object Date:
  private def isLeap(year: Int): Boolean =
    def isMultipleOf(x: Int, y: Int): Boolean = x % y == 0

    (isMultipleOf(4, year) && !isMultipleOf(100, year)) || isMultipleOf(400, year)

  private def days(month: Int, year: Int): Int = month match
    case 1 | 3 | 5 | 7 | 8 | 10 | 12 => 31
    case 4 | 6 | 9 | 11 => 31
    case 2 => if isLeap(year) then 29 else 28


@main def dateTest(): Unit =
  val d1 = Date(1, 1, 2011)
  val d2 = Date(1, 2, 2020)
  val d3 = Date(1, 1, 2011)
  val d4 = Date(4, 11, 2015)

  println(d1 == d2)
  println(d1 == d3)

  println(d1 < d2)
  println(d2 < d2)

  val ds = List(d1, d2, d3, d4)
  val ds1 = ds.sorted
  println(ds1)

  val ds2 = ds.sorted(summon[Ordering[Date]].reverse)
  println(ds2)
  
  println(ds.min)
  println(ds.max)