/*
  Pepe Gallardo, 2023
 */

package charts

// Demonstrates creating a pie chart from labeled percentage data.

// Pie chart showing the distribution of student marks across grade categories
@main def pieChartTest(): Unit =
  import inform.graphics.plot.*

  val pieDataset = PieDataset()
  pieDataset += ("Fail", 35)
  pieDataset += ("Pass", 30)
  pieDataset += ("Remarkable", 25)
  pieDataset += ("Outstanding", 10)

  val chart = PieChart("Marks", pieDataset)
  chart.draw(600, 400)
