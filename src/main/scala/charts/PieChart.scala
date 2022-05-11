/*
  Pepe Gallardo, 2022
 */

package charts

@main def pieChartTest(): Unit =
  import inform.graphics.plot.*
  
  val pieDataset = PieDataset()
  pieDataset += ("Fail", 35)
  pieDataset += ("Pass", 30)
  pieDataset += ("Remarkable", 25)
  pieDataset += ("Outstanding", 10)

  val chart = PieChart("Marks", pieDataset)
  chart.draw(600, 400)
