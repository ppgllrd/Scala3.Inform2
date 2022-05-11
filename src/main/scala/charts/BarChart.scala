/*
  Pepe Gallardo, 2022
 */

package charts

@main def barChartTest1(): Unit =
  import inform.graphics.plot.*
  
  val series = XYSeries("Values")
  series += (0,10)
  series += (1,50)
  series += (2,30)
  series += (3,100)
  series += (4,80)

  val seriesColl = XYSeriesCollection()
  seriesColl += series

  val chart = XYBarChart("Bars", "X", "Y", seriesColl)
  chart.draw(300, 400)


@main def barChartTest2(): Unit =
  import inform.graphics.color.*
  import inform.graphics.plot.*

  val series1 = XYSeries("Values1")
  series1 += (0,10)
  series1 += (1,50)
  series1 += (2,30)
  series1 += (3,100)
  series1 += (4,80)

  val series2 = XYSeries("Values2")
  series2 += (5,40)
  series2 += (6,60)
  series2 += (7,10)

  val seriesColl = XYSeriesCollection()
  seriesColl += series1
  seriesColl += series2

  val chart = XYBarChart("Bars", "X", "Y", seriesColl)
  chart.config(series1, color = Color(50,180,220), outlineColor = Color.blue)
  chart.config(series2, color = Color(250,105,105), outlineColor = Color.red)
  chart.draw(400,400)
