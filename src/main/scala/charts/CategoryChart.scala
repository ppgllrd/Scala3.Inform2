/*
  Pepe Gallardo, 2022
 */

package charts

@main def categoryChartTest(): Unit =
  import inform.graphics.plot.*
  import inform.graphics.color.*

  type Sex = String      // groups
  val males = "Males"
  val females = "Females"

  type Country = String      // categories
  val spain = "Spain"
  val france = "France"
  val italy = "Italy"

  val data = CategoryDataset.withType[Double, Sex, Country]()
  data += (1000, males, spain)  // (value, group, category)
  data += (2000, males, france)
  data += (3000, males, italy)

  data += (1100, females, spain)
  data += (2100, females, france)
  data += (3100, females, italy)

  val chart = BarChart("Bars", "X", "Y", data)
  chart.config(males, color = Color(50,180,220), outlineColor = Color.blue)
  chart.config(females, color = Color(250,105,105), outlineColor = Color.red)
  chart.draw(400,400)