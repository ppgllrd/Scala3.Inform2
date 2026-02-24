/*
  Pepe Gallardo, 2023
 */

package charts

// Demonstrates creating XY line charts with discrete data points and continuous mathematical functions.

// Simple line chart with two series of discrete data points
@main def lineChartTest1(): Unit =
  import inform.graphics.plot.*

  val series1 = XYSeries("Line 1")
  series1 += (1, 100)
  series1 += (2, 150)
  series1 += (3, 25)

  val series2 = XYSeries("Line 2")
  series2 += (1, 175)
  series2 += (2, 100)
  series2 += (3, 125)

  val seriesColl = XYSeriesCollection()
  seriesColl += series1
  seriesColl += series2

  val chart = XYLineChart("Lines", "X", "Y", seriesColl)
  chart.draw(500, 300)

// Line chart plotting mathematical functions: sin(x)/x and 3·sin(x)/x (sinc-like functions)
@main def lineChartTest2(): Unit =
  import inform.graphics.plot.*
  import scala.math.*

  val series1 = XYSeries("sin(x)/x")
  val series2 = XYSeries("3·sin(x)/x")

  val xMin = -5 * Pi // min x
  val xMax = 5 * Pi // max x
  val delta = 0.01 // distance between 2 points

  // Sample both functions at small intervals to produce smooth curves
  var x = xMin
  while x <= xMax do
    series1 += (x, sin(x) / x)       // sinc-like function
    series2 += (x, 3 * sin(x) / x)   // scaled version (3× amplitude)
    x += delta

  val seriesColl = XYSeriesCollection()
  seriesColl += series1
  seriesColl += series2

  val chart = XYLineChart("Functions", "X", "Y", seriesColl)
  chart.draw(500, 300)
