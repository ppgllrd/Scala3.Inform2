/** **************************************************************************************
  * Informática. Grado en Matemáticas. Universidad de Málaga. \@ Pepe Gallardo
  *
  * Simple charts based on JFreeChart
  */

package inform.graphics.plot

import inform.graphics.draw2D
import inform.graphics.GraphicsWindow
import inform.graphics.plot.Orientation.Orientation
import org.jfree.chart.axis.{LogAxis, LogarithmicAxis, NumberAxis, NumberTickUnit}
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.renderer.category.{BarRenderer, StandardBarPainter}
import org.jfree.chart.renderer.xy.{StandardXYBarPainter, XYBarRenderer, XYLineAndShapeRenderer}
import org.jfree.chart.{ChartFactory, StandardChartTheme}
import org.jfree.data.category.DefaultCategoryDataset
import org.jfree.data.general.DefaultPieDataset
import org.jfree.data.statistics.HistogramType

import java.awt.{BasicStroke, Color, Font}
import java.lang

type HistogramDataset = org.jfree.data.statistics.HistogramDataset
type JFreeChart = org.jfree.chart.JFreeChart
type Plot = org.jfree.chart.plot.Plot
type XYPlot = org.jfree.chart.plot.XYPlot

private object PlotUtils {
  def setXYBarStyle(chart: JFreeChart): Unit = {
    val renderer = chart.getXYPlot.getRenderer().asInstanceOf[XYBarRenderer]
    renderer.setDrawBarOutline(true)
    renderer.setShadowVisible(false)
    // renderer.setOutlinePaint(java.awt.Color.GRAY)
    renderer.setBarPainter(new StandardXYBarPainter())
    for (i <- 0 until chart.getXYPlot.getDataset.getSeriesCount)
      renderer.setSeriesOutlineStroke(i, new BasicStroke(2.5f))
  }

  def setBarStyle(chart: JFreeChart): Unit = {
    val renderer = chart.getCategoryPlot.getRenderer().asInstanceOf[BarRenderer]
    renderer.setDrawBarOutline(true)
    renderer.setShadowVisible(false)
    // renderer.setOutlinePaint(java.awt.Color.GRAY)
    renderer.setBarPainter(new StandardBarPainter())
    for (
      i <-
        0 until chart.getCategoryPlot.getDataset.getRowCount * chart.getCategoryPlot.getDataset.getColumnCount
    )
      renderer.setSeriesOutlineStroke(i, new BasicStroke(2.5f))
  }

  def setXYStyle(chart: JFreeChart): Unit = {
    val renderer = chart.getXYPlot
      .getRenderer()
      .asInstanceOf[XYLineAndShapeRenderer] // new XYLineAndShapeRenderer()
    for (i <- 0 until chart.getXYPlot.getDataset.getSeriesCount)
      renderer.setSeriesStroke(i, stroke(2.5f))
  }

  def stroke(width: Double) = new BasicStroke(
    width.toFloat,
    BasicStroke.CAP_ROUND,
    BasicStroke.JOIN_ROUND
  )

  def setTheme(chart: JFreeChart, fontName: String = "Arial"): Unit = {
    val chartTheme = org.jfree.chart.StandardChartTheme
      .createJFreeTheme()
      .asInstanceOf[StandardChartTheme]

    def font(sz: Int, bold: Boolean = false) =
      new Font(fontName, if (bold) Font.BOLD else Font.PLAIN, sz)

    val color = new Color(0, 0, 0)

    chartTheme.setExtraLargeFont(font(21, bold = true))
    chartTheme.setLargeFont(font(18, bold = true))
    chartTheme.setRegularFont(font(15))
    chartTheme.setSmallFont(font(12))

    chartTheme.setAxisLabelPaint(color)
    chartTheme.setLegendItemPaint(color)
    chartTheme.setItemLabelPaint(color)

    chartTheme.setXYBarPainter(new StandardXYBarPainter())

    chartTheme.setPlotBackgroundPaint(new Color(220, 220, 220))
    chartTheme.setLegendBackgroundPaint(new Color(230, 230, 230))

    chartTheme.setRangeGridlinePaint(java.awt.Color.gray)
    chartTheme.setDomainGridlinePaint(java.awt.Color.gray)

    chartTheme.apply(chart)
  }
}

trait CanDraw {
  private val defaultWidth = 600
  private val defaultHeight = 400
  private val defaultTitle = "inform.graphics.plot"

  def draw(g2D: java.awt.Graphics2D, rect: java.awt.geom.Rectangle2D): Unit

  /** Draws chart in a graphic window with specified dimensions.
    *
    * @param width
    *   width of window.
    *
    * @param height
    *   height of window.
    */
  def draw(width: Int, height: Int): Unit =
    draw(defaultTitle, width, height)

  /** Draws chart in a graphic window with specified dimensions.
    *
    * @param title
    *   title of window.
    *
    * @param width
    *   width of window.
    *
    * @param height
    *   height of window.
    */
  def draw(
      title: String = defaultTitle,
      width: Int = defaultWidth,
      height: Int = defaultHeight
  ): Unit = {
    val window = GraphicsWindow(title, width, height)
    window.draw(this)
  }

  /** Draws chart in a graphic window with default dimensions.
    */
  def draw(): Unit = {
    draw(defaultTitle, defaultWidth, defaultHeight)
  }
}

/** A series of numeric data with X and Y components.
  *
  * @param label
  *   label of series.
  */
class XYSeries[Label <: Comparable[Label]](label: Label)
    extends org.jfree.data.xy.XYSeries(label)
    with Iterable[(Double, Double)] {

  /** Adds an X and Y value to this series of data.
    *
    * @param t
    *   tuple with X and Y values to add.
    */
  def +=[X: Numeric, Y: Numeric](t: (X, Y)): Unit =
    super.add(t._1.asInstanceOf[Number], t._2.asInstanceOf[Number])

  /** checks if this series is empty.
    *
    * @return
    *   true if this series is empty.
    */
  override def isEmpty: Boolean =
    super.isEmpty

  /** An iterator for iterating through the X and Y values of this series.
    *
    * @return
    *   An iterator for iterating through the X and Y values of this series.
    */
  override def iterator: Iterator[(Double, Double)] =
    new Iterator[(Double, Double)] {
      private var i = 0

      override def hasNext: Boolean =
        i < getItemCount

      override def next(): (Double, Double) = {
        if (!hasNext)
          throw new NoSuchElementException
        val x = getX(i).doubleValue()
        val y = getY(i).doubleValue()
        i += 1
        (x, y)
      }
    }
}

object XYSeries {

  /** A series of data with numeric X and Y components.
    *
    * @param label
    *   label for this series.
    * @return
    *   A series of data with numeric X and Y components.
    */
  def apply[Label <: Comparable[Label]](label: Label) = new XYSeries(label)

  /** Creates a series of data from two numeric iterables with X and Y components.
    *
    * @param label
    *   label for this series.
    * @param xs
    *   Iterable with X values.
    * @param ys
    *   Iterable with Y values.
    * @return
    *   A series of data from two numeric iterables with X and Y components.
    */
  def apply[Label <: Comparable[Label], X: Numeric, Y: Numeric](
      label: Label,
      xs: Iterable[X],
      ys: Iterable[Y]
  ): XYSeries[Label] =
    apply(label, xs.zip(ys))

  /** Creates a series of data from an iterable of tuples with X and Y components.
    *
    * @param label
    *   label for this series.
    * @param xys
    *   Iterable with tuples of X and Y values.
    * @return
    *   A series of data from an iterable of tuples with X and Y components.
    */
  def apply[Label <: Comparable[Label], X: Numeric, Y: Numeric](
      label: Label,
      xys: Iterable[(X, Y)]
  ): XYSeries[Label] = {
    val xySeries = new XYSeries(label)
    for ((x, y) <- xys)
      xySeries += (x, y)
    xySeries
  }
}

/** A collection of `XYSeries`.
  */
class XYSeriesCollection extends org.jfree.data.xy.XYSeriesCollection with Iterable[XYSeries[_]] {
  override def clone: AnyRef = super.clone()

  /** Adds an `XYSeries`to this collection of series.
    *
    * @param xySeries
    */
  def +=(xySeries: org.jfree.data.xy.XYSeries): Unit = super.addSeries(xySeries)

  /** An iterator for iterating through the `XYSeries` of this collection.
    * @return
    *   An iterator for iterating through the `XYSeries` of this collection.
    */
  override def iterator: Iterator[XYSeries[_]] = new Iterator[XYSeries[_]] {
    private var i = 0

    override def hasNext: Boolean =
      i < getSeriesCount

    override def next(): XYSeries[_] = {
      if (!hasNext)
        throw new NoSuchElementException
      val series = getSeries(i).asInstanceOf[XYSeries[_]]
      i += 1
      series
    }
  }
}

object XYSeriesCollection {

  /** Constructs an empty collection of `XYSeries`.
    *
    * @return
    *   an empty collection of `XYSeries`.
    */
  def apply() = new XYSeriesCollection

  /** Constructs a collection of `XYSeries` from a varargs of `XYSeries`.
    *
    * @param xys
    *   varargs of `XYSeries`.
    * @return
    *   a collection of `XYSeries` from a varargs of `XYSeries`.
    */
  def apply(xys: XYSeries[_]*): XYSeriesCollection = apply(xys)

  /** Constructs a collection of `XYSeries` from an iterable of `XYSeries`.
    *
    * @param xys
    *   iterable of `XYSeries`.
    * @return
    *   a collection of `XYSeries` from an iterable of `XYSeries`.
    */
  def apply(xys: Iterable[XYSeries[_]]): XYSeriesCollection = {
    val seriesColl = new XYSeriesCollection
    xys.foreach(seriesColl.addSeries(_))
    seriesColl
  }
}

/** Enumeration for representing the orientation of a chart.
  */
object Orientation extends Enumeration {
  type Orientation = Value
  val Horizontal, Vertical = Value
}

trait HasOrientation {
  val chart: JFreeChart

  def orientation: Orientation =
    if (chart.getXYPlot.getOrientation == PlotOrientation.HORIZONTAL)
      Orientation.Horizontal
    else
      Orientation.Vertical

  def orientation_=(o: Orientation): Unit = {
    val po =
      if (o == Orientation.Horizontal)
        PlotOrientation.HORIZONTAL
      else
        PlotOrientation.VERTICAL
    chart.getXYPlot.setOrientation(po)
  }
}

trait HasXYAxis {
  val chart: JFreeChart
  val domainMargin: Double

  def setRangeAxis(
      yMin: Double,
      yMax: Double,
      tickUnit: Double = -1.0,
      log: Boolean = false
  ): Unit = {
    val plot: XYPlot = chart.getXYPlot
    val label = plot.getRangeAxis().getLabel
    val tickFont = plot.getRangeAxis().getTickLabelFont
    val labelFont = plot.getRangeAxis().getLabelFont
    val axis =
      if (log) new LogarithmicAxis(label)
      else new NumberAxis(label)

    val margin = 0
    axis.setRange(yMin - margin, yMax + margin)
    if (!log && tickUnit > 0)
      axis.setTickUnit(new NumberTickUnit(tickUnit))
    axis.setTickLabelFont(tickFont)
    axis.setLabelFont(labelFont)

    plot.setRangeAxis(axis)
  }

  def rangeAxisLog(): Unit = {
    val plot: XYPlot = chart.getXYPlot
    val label = plot.getRangeAxis().getLabel
    val tickFont = plot.getRangeAxis().getTickLabelFont
    val labelFont = plot.getRangeAxis().getLabelFont
    val axis = new LogarithmicAxis(label)

    axis.setTickLabelFont(tickFont)
    axis.setLabelFont(labelFont)

    plot.setRangeAxis(axis)
  }

  def setDomainAxis(
      xMin: Double,
      xMax: Double,
      tickUnit: Double = -1.0,
      log: Boolean = false
  ): Unit = {
    val plot: XYPlot = chart.getXYPlot
    val label = plot.getDomainAxis().getLabel
    val tickFont = plot.getDomainAxis().getTickLabelFont
    val labelFont = plot.getDomainAxis().getLabelFont

    val axis =
      if (log) new LogarithmicAxis(label)
      else new NumberAxis(label)

    axis.setRange(xMin - domainMargin, xMax + domainMargin)

    if (!log && tickUnit > 0)
      axis.setTickUnit(new NumberTickUnit(tickUnit))
    axis.setTickLabelFont(tickFont)
    axis.setLabelFont(labelFont)

    plot.setDomainAxis(axis)
  }

  def domainAxisLog(): Unit = {
    val plot: XYPlot = chart.getXYPlot
    val label = plot.getDomainAxis().getLabel
    val tickFont = plot.getDomainAxis().getTickLabelFont
    val labelFont = plot.getDomainAxis().getLabelFont
    val axis = new LogarithmicAxis(label)

    axis.setTickLabelFont(tickFont)
    axis.setLabelFont(labelFont)

    plot.setDomainAxis(axis)
  }
}

/** A charts consisting of different series of data points connected by lines.
  *
  * @param chart
  *   the underlying JFreeChart object.
  */
class XYLineChart(val chart: JFreeChart)
    extends JFreeChart(chart.getTitle.getText, chart.getPlot)
    with CanDraw
    with HasOrientation
    with HasXYAxis {

  val domainMargin = 0.0

  def setSeriesLinesVisible(numSeries: Int, visible: Boolean): Unit = {
    val plot: XYPlot = chart.getXYPlot
    val renderer =
      plot
        .getRenderer()
        .asInstanceOf[XYLineAndShapeRenderer] // new XYLineAndShapeRenderer()
    renderer.setSeriesLinesVisible(numSeries, visible)
  }

  def setSeriesShapesVisible(numSeries: Int, visible: Boolean): Unit = {
    val plot: XYPlot = chart.getXYPlot
    val renderer =
      plot
        .getRenderer()
        .asInstanceOf[XYLineAndShapeRenderer] // new XYLineAndShapeRenderer()
    renderer.setSeriesShapesVisible(numSeries, visible)
  }

  def setSeriesStrokeWidth(numSeries: Int, width: Double): Unit = {
    val plot: XYPlot = chart.getXYPlot
    val renderer =
      plot
        .getRenderer()
        .asInstanceOf[XYLineAndShapeRenderer] // new XYLineAndShapeRenderer()
    renderer.setSeriesStroke(numSeries, PlotUtils.stroke(width))
  }

  /** Configures the line and shape visibility, stroke width, and color of a series.
    *
    * @param xySeries
    *   the series to configure.
    * @param lineVisible
    *   whether the line is visible.
    * @param shapeVisible
    *   whether the shape is visible.
    * @param strokeWidth
    *   the stroke width.
    * @param color
    *   the color.
    */
  def config(
      xySeries: XYSeries[_],
      lineVisible: Boolean = true,
      shapeVisible: Boolean = false,
      strokeWidth: Double = -1.0,
      color: Color = null
  ): Unit = {
    val plot: XYPlot = chart.getXYPlot
    val numSeries = plot.getDataset.indexOf(xySeries.getKey)

    config(numSeries, lineVisible, shapeVisible, strokeWidth, color)
  }

  def config(
      numSeries: Int,
      lineVisible: Boolean,
      shapeVisible: Boolean,
      strokeWidth: Double,
      color: Color
  ): Unit = {
    val plot: XYPlot = chart.getXYPlot
    val renderer =
      plot
        .getRenderer()
        .asInstanceOf[XYLineAndShapeRenderer] // new XYLineAndShapeRenderer()

    renderer.setSeriesLinesVisible(numSeries, lineVisible)
    renderer.setSeriesShapesVisible(numSeries, shapeVisible)
    if (strokeWidth >= 0)
      renderer.setSeriesStroke(numSeries, PlotUtils.stroke(strokeWidth))
    if (color != null)
      renderer.setSeriesPaint(numSeries, color)
  }
}

object XYLineChart {

  /** Creates a chart consisting of different series of data points connected by lines from an
    * `Iterable` of X and Y values.
    *
    * @param title
    *   the chart title.
    * @param domainLabel
    *   the domain axis label.
    * @param rangeLabel
    *   the range axis label.
    * @param seriesLabel
    *   the series label.
    * @param data
    *   the `Iterable` with the data.
    * @return
    *   a new line chart with the given data.
    */
  def apply[X: Numeric, Y: Numeric](
      title: String,
      domainLabel: String,
      rangeLabel: String,
      seriesLabel: String,
      data: Iterable[(X, Y)]
  ): XYLineChart = {
    val series = XYSeries(seriesLabel)
    for (kv <- data)
      series += kv
    apply(title, domainLabel, rangeLabel, series)
  }

  /** Creates a chart consisting of different series of data points connected by lines from a
    * `XYSeries`.
    *
    * @param title
    *   the chart title.
    * @param domainLabel
    *   the domain axis label.
    * @param rangeLabel
    *   the range axis label.
    * @param data
    *   the `XYSeries` wit the data.
    * @return
    *   a new line chart with the given data.
    */
  def apply(
      title: String,
      domainLabel: String,
      rangeLabel: String,
      data: XYSeries[_]
  ): XYLineChart = {
    val collection = XYSeriesCollection()
    collection.addSeries(data)
    apply(title, domainLabel, rangeLabel, collection, true)
  }

  /** Creates a chart consisting of different series of data points connected by lines from a
    * `XYSeriesCollection`.
    *
    * @param title
    *   the chart title.
    * @param domainLabel
    *   the domain axis label.
    * @param rangeLabel
    *   the range axis label.
    * @param data
    *   the `XYSeriesCollection` with the data.
    * @param vertical
    *   true if the chart is vertical, false if horizontal.
    * @return
    *   a new line chart with the given data.
    */
  def apply(
      title: String,
      domainLabel: String,
      rangeLabel: String,
      data: XYSeriesCollection,
      vertical: Boolean = true
  ): XYLineChart = {
    val chart = ChartFactory.createXYLineChart(
      title,
      domainLabel,
      rangeLabel,
      data,
      if (vertical) PlotOrientation.VERTICAL else PlotOrientation.HORIZONTAL,
      true,
      false,
      false
    )
    PlotUtils.setTheme(chart)
    PlotUtils.setXYStyle(chart)
    new XYLineChart(chart)
  }
}

/** A chart consisting of different bars.
  */
class XYBarChart(val chart: JFreeChart)
    extends JFreeChart(chart.getTitle.getText, chart.getPlot)
    with CanDraw
    with HasOrientation
    with HasXYAxis {

  val domainMargin = 0.8

  /** Configure the color, outline color and stroke width of a series.
    *
    * @param xySeries
    *   the series to configure.
    * @param color
    *   the color of the bars.
    * @param outlineColor
    *   the color of the outline of the bars.
    * @param strokeWidth
    *   the width of the outline of the bars.
    */
  def config(
      xySeries: XYSeries[_],
      color: Color = null,
      outlineColor: Color = null,
      strokeWidth: Double = -1.0
  ): Unit = {
    val plot: XYPlot = chart.getXYPlot
    val numSeries = plot.getDataset.indexOf(xySeries.getKey)
    config(numSeries, color, outlineColor, strokeWidth)
  }

  def config(
      numSeries: Int,
      color: Color,
      outlineColor: Color,
      strokeWidth: Double
  ): Unit = {
    val plot: XYPlot = chart.getXYPlot
    val renderer = plot.getRenderer().asInstanceOf[XYBarRenderer]

    renderer.setDrawBarOutline(true)
    if (color != null)
      renderer.setSeriesPaint(numSeries, color)
    if (outlineColor != null) {
      renderer.setDrawBarOutline(true)
      renderer.setSeriesOutlinePaint(numSeries, outlineColor)
    }
    if (strokeWidth >= 0)
      renderer.setSeriesOutlineStroke(
        numSeries,
        new BasicStroke(strokeWidth.toFloat)
      )
  }

  /** Configures the stroke width of the outline of all series in chart.
    *
    * @param strokeWidth
    */
  def config(strokeWidth: Double): Unit = {
    val renderer = chart.getXYPlot.getRenderer().asInstanceOf[XYBarRenderer]

    if (strokeWidth >= 0)
      for (i <- 0 until chart.getXYPlot.getDataset.getSeriesCount)
        renderer.setSeriesOutlineStroke(i, new BasicStroke(strokeWidth.toFloat))
  }
}

object XYBarChart {

  /** Creates a bar chart from a `XYSeriesCollection` of data.
    *
    * @param title
    *   title to show on top of chart.
    * @param domainLabel
    *   label to show on X axis.
    * @param rangeLabel
    *   label to show on Y axis.
    * @param data
    *   collection of series to plot in chart.
    * @param vertical
    *   whether bars should be shown vertically.
    * @return
    *   a bar chart with provided data.
    */
  def apply(
      title: String,
      domainLabel: String,
      rangeLabel: String,
      data: XYSeriesCollection,
      vertical: Boolean = true
  ): XYBarChart = {
    val chart = ChartFactory.createXYBarChart(
      title,
      domainLabel,
      false,
      rangeLabel,
      data,
      if (vertical) PlotOrientation.VERTICAL else PlotOrientation.HORIZONTAL,
      true,
      false,
      false
    )
    PlotUtils.setTheme(chart)
    PlotUtils.setXYBarStyle(chart)
    new XYBarChart(chart)
  }

  /** Creates a bar chart from an `Iterable` of data.
    *
    * @param title
    *   title to show on top of chart.
    * @param domainLabel
    *   label to show on X axis.
    * @param rangeLabel
    *   label to show on Y axis.
    * @param seriesLabel
    *   label to show on legend.
    * @param data
    *   collection of series to plot in chart.
    * @return
    *   a bar chart with provided data.
    */
  def apply[X: Numeric, Y: Numeric](
      title: String,
      domainLabel: String,
      rangeLabel: String,
      seriesLabel: String,
      data: Iterable[(X, Y)]
  ): XYBarChart = {
    val series = XYSeries(seriesLabel)
    for (kv <- data)
      series += kv
    apply(title, domainLabel, rangeLabel, series)
  }

  /** Creates a bar chart from an `XYSeries`.
    *
    * @param title
    *   title to show on top of chart.
    * @param domainLabel
    *   label to show on X axis.
    * @param rangeLabel
    *   label to show on Y axis.
    * @param data
    *   series to plot in chart.
    * @return
    *   a bar chart with provided data.
    */
  def apply(
      title: String,
      domainLabel: String,
      rangeLabel: String,
      data: XYSeries[_]
  ): XYBarChart = {
    val collection = XYSeriesCollection()
    collection.addSeries(data)
    apply(title, domainLabel, rangeLabel, collection, true)
  }

}

object PieDataset {

  /** Creates an empty pie dataset.
    *
    * @return
    *   an empty pie dataset.
    */
  def apply() = new PieDataset[String, Double]

  /** Creates an empty pie dataset with a specific type for the labels and values.
    *
    * @return
    *   an empty pie dataset.
    */
  def withType[Label <: Comparable[Label], V: Numeric]() =
    new PieDataset[Label, V]

  /** Creates a pie dataset from an `Iterable` of labels and an `Iterable` of values.
    * @param labels
    *   the labels of the pie slices.
    * @param values
    *   the values of the pie slices.
    * @return
    *   a pie dataset with provided data.
    */
  def apply[Label <: Comparable[Label], V: Numeric](
      labels: Iterable[Label],
      values: Iterable[V]
  ): PieDataset[Label, V] =
    apply(labels zip values)

  /** Creates a pie dataset from an `Iterable` of labels and values.
    *
    * @param etVs
    *   the labels and values of the pie slices.
    * @return
    *   a pie dataset with provided data.
    */
  def apply[Label <: Comparable[Label], V: Numeric](
      etVs: Iterable[(Label, V)]
  ): PieDataset[Label, V] = {
    val pieDS = new PieDataset[Label, V]
    for ((lb, v) <- etVs)
      pieDS.add(lb, v)
    pieDS
  }

  /** Creates a pie dataset from a sequence of labels and values.
    *
    * @param etVs
    *   the labels and values of the pie slices.
    * @return
    *   a pie dataset with provided data.
    */
  def apply[Label <: Comparable[Label], V: Numeric](
      etVs: (Label, V)*
  ): PieDataset[Label, V] = apply(etVs)
}

/** A dataset for pie charts.
  */
class PieDataset[Label <: Comparable[Label], V: Numeric]
    extends DefaultPieDataset[Label]
    with Iterable[(Label, V)] {
  override def clone: AnyRef = super.clone()

  def add(label: Label, value: V): Unit =
    super.setValue(label, value.asInstanceOf[Number])

  /** Adds a new label and its value to the dataset.
    *
    * @param t
    *   the label and value to add.
    */
  def +=(t: (Label, V)): Unit = super.setValue(t._1, t._2.asInstanceOf[Number])

  /** Adds a new label and its value to the dataset.
    *
    * @param label
    *   the label to add.
    * @param value
    *   the value to add.
    */
  def +=(label: Label, value: V): Unit =
    super.setValue(label, value.asInstanceOf[Number])

    /** An iterator over the labels and values of the dataset.
      * @return
      *   an iterator over the labels and values of the dataset.
      */
  override def iterator: Iterator[(Label, V)] = new Iterator[(Label, V)] {
    private var i = 0

    override def hasNext: Boolean =
      i < getItemCount

    override def next(): (Label, V) = {
      if (!hasNext)
        throw new NoSuchElementException
      val k = getKey(i)
      val v = getValue(i).asInstanceOf[V]
      i += 1
      (k, v)
    }
  }
}

/** A pie chart.
  */
class PieChart(chart: JFreeChart)
    extends JFreeChart(chart.getTitle.getText, chart.getPlot)
    with CanDraw {
  @annotation.nowarn
  /** Creates a pie chart with a specific title and data.
    * @param title
    *   the title of the chart.
    * @param data
    *   the data of the chart.
    */
  def this(title: String, data: PieDataset[_, _]) = {
    this(ChartFactory.createPieChart3D(title, data, true, false, false))
    PlotUtils.setTheme(this)
  }
}

object PieChart {

  /** Creates a pie chart with a specific title and data.
    *
    * @param title
    *   the title of the chart.
    * @param map
    *   an immutable `Map` with the data of the chart.
    * @return
    *   a pie chart with provided data.
    */
  def apply[Label <: Comparable[Label], V: Numeric](
      title: String,
      map: scala.collection.immutable.Map[Label, V]
  ): PieChart = {
    val pieDataset = new PieDataset[Label, V]()
    for ((k, v) <- map)
      pieDataset.add(k, v)
    apply(title, pieDataset)
  }

  /** Creates a pie chart with a specific title and data.
    *
    * @param title
    *   the title of the chart.
    * @param data
    *   a PieDataset` with the data of the chart.
    * @return
    *   a pie chart with provided data.
    */
  def apply(title: String, data: PieDataset[_, _]) =
    new PieChart(title, data)
}

object HistogramDataset {

  /** Creates an empty histogram dataset.
    *
    * @param relativeFreq
    *   if true, the histogram will be a relative frequency histogram.
    * @return
    *   an empty histogram dataset.
    */
  def apply(relativeFreq: Boolean = false): HistogramDataset = {
    val hds = new HistogramDataset()
    hds.setType(
      if (relativeFreq) HistogramType.RELATIVE_FREQUENCY
      else HistogramType.FREQUENCY
    )
    hds
  }
}

class HistogramChart(val chart: JFreeChart)
    extends JFreeChart(chart.getTitle.getText, chart.getPlot)
    with CanDraw
    with HasOrientation
    with HasXYAxis {

  val domainMargin = 0.5

  /** Configures the histogram with the provided parameters.
    *
    * @param color
    *   the color of the bars.
    * @param outlineColor
    *   the color of the outline of the bars.
    * @param strokeWidth
    *   the width of the outline of the bars.
    * @param relativeFreq
    *   if true, the histogram will be a relative frequency histogram.
    */
  def config(
      color: Color = null,
      outlineColor: Color = null,
      strokeWidth: Double = -1.0,
      relativeFreq: Boolean = false
  ): Unit = {
    val renderer = chart.getXYPlot.getRenderer().asInstanceOf[XYBarRenderer]
    renderer.setDrawBarOutline(true)

    if (color != null)
      renderer.setSeriesPaint(0, color)

    if (outlineColor != null) {
      renderer.setDrawBarOutline(true)
      renderer.setSeriesOutlinePaint(0, outlineColor)
    }

    val histType =
      if (relativeFreq)
        HistogramType.RELATIVE_FREQUENCY
      else
        HistogramType.FREQUENCY
    chart.getXYPlot.getDataset.asInstanceOf[HistogramDataset].setType(histType)

    if (strokeWidth >= 0)
      for (i <- 0 until chart.getXYPlot.getDataset.getSeriesCount)
        renderer.setSeriesOutlineStroke(i, new BasicStroke(strokeWidth.toFloat))
  }
}

object HistogramChart {

  /** Creates a histogram chart with a specific title and data.
    *
    * @param title
    *   the title of the chart.
    * @param domainLabel
    *   the label of the domain axis.
    * @param rangeLabel
    *   the label of the range axis.
    * @param data
    *   the data of the chart.
    * @param dataLabel
    *   the label of the data.
    * @param buckets
    *   the number of buckets.
    * @param toDouble
    *   a function that converts the data to a double.
    * @return
    *   a histogram chart with provided data.
    */
  def apply[Y](
      title: String,
      domainLabel: String,
      rangeLabel: String,
      data: Iterable[Y],
      dataLabel: String = null,
      buckets: Int = -1
  )(implicit toDouble: Y => Double): HistogramChart = {
    val dataset = HistogramDataset()
    val xs: Array[Double] = data.map(toDouble(_)).toArray

    val numBuckets = if (buckets > 0) buckets else data.size

    dataset.addSeries(
      if (dataLabel == null) title else dataLabel,
      xs,
      numBuckets
    )

    apply(title, domainLabel, rangeLabel, dataset)
  }

  /** Creates a histogram chart with a specific title and data.
    *
    * @param title
    *   the title of the chart.
    * @param domainLabel
    *   the label of the domain axis.
    * @param rangeLabel
    *   the label of the range axis.
    * @param dataset
    *   the data of the chart.
    * @return
    *   a histogram chart with provided data.
    */
  def apply(
      title: String,
      domainLabel: String,
      rangeLabel: String,
      dataset: HistogramDataset
  ): HistogramChart = {
    val chart = ChartFactory.createHistogram(
      title,
      domainLabel,
      rangeLabel,
      dataset,
      PlotOrientation.VERTICAL,
      true,
      false,
      false
    )
    PlotUtils.setTheme(chart)
    PlotUtils.setXYBarStyle(chart)
    val hChart = new HistogramChart(chart)

    val numSeries = dataset.getSeriesCount

    var xMin = dataset.getStartX(0, 0).doubleValue()
    var xMax = dataset.getEndX(0, dataset.getItemCount(0) - 1).doubleValue()
    for (i <- 1 until numSeries) {
      xMin = xMin min dataset.getStartX(i, 0).doubleValue()
      xMax = xMax max dataset.getEndX(i, dataset.getItemCount(0) - 1).doubleValue()
    }

    val plot: XYPlot = hChart.getXYPlot

    plot.setRangeZeroBaselineVisible(false)
    plot.setDomainZeroBaselineVisible(false)

    hChart.config(color = new Color(0, 190, 230), outlineColor = Color.blue)
    hChart.setDomainAxis(xMin, xMax)
    hChart
  }
}

object JFreeChart {
  def apply(title: String, plot: Plot): JFreeChart = {
    val chart = new JFreeChart(title, plot)
    PlotUtils.setTheme(chart)
    chart
  }
}

/** A bar chart with different groups and categories.
  *
  * @param chart
  *   the JFreeChart object.
  */
class CategoryDataset[V: Numeric, Group <: Comparable[Group], Category <: Comparable[
  Category
]] extends DefaultCategoryDataset
    with Iterable[(V, Group, Category)] {
  override def clone: AnyRef = super.clone()

  /** Adds a value to the category dataset.
    *
    * @param value
    *   the value.
    * @param group
    *   the group.
    * @param category
    *   the category.
    */
  def add(value: V, group: Group, category: Category): Unit =
    super.setValue(value.asInstanceOf[Number], group, category)

  /** Adds a value to the category dataset.
    *
    * @param value
    *   the value.
    * @param group
    *   the group.
    * @param category
    *   the category.
    */
  def +=(value: V, group: Group, category: Category): Unit =
    super.setValue(value.asInstanceOf[Number], group, category)

  override def iterator: Iterator[(V, Group, Category)] = new Iterator[(V, Group, Category)] {
    private var i = 0
    private var j = 0

    override def hasNext: Boolean =
      i < getRowCount && j < getColumnCount

    override def next(): (V, Group, Category) = {
      if (!hasNext)
        throw new NoSuchElementException

      val v = getValue(i, j).asInstanceOf[V]
      val rk = getRowKey(i).asInstanceOf[Group]
      val ck = getColumnKey(j).asInstanceOf[Category]

      if (j < getColumnCount - 1)
        j += 1
      else {
        i += 1
        j = 0
      }

      (v, rk, ck)
    }
  }
}

/** A CategoryDataset is a table that contains values associated with groups and categories.
  */
object CategoryDataset {

  /** Creates a new CategoryDataset with the default type parameters.
    *
    * @return
    *   a new CategoryDataset with the default type parameters.
    */
  def apply() = new CategoryDataset[Double, String, String]

  /** Creates a new CategoryDataset with the given type parameters.
    *
    * @param values
    *   the values of the chart.
    * @param groups
    *   the groups of the chart.
    * @param categories
    *   the categories of the chart.
    * @return
    *   a new CategoryDataset with the given type parameters.
    */
  def apply[V: Numeric, Group <: Comparable[Group], Category <: Comparable[Category]](
      values: Iterable[V],
      groups: Iterable[Group],
      categories: Iterable[Category]
  ): CategoryDataset[V, Group, Category] =
    apply(values.lazyZip(groups).lazyZip(categories))

  /** Creates a new CategoryDataset with the given type parameters.
    *
    * @param vgcs
    *   the values, groups and categories of the chart.
    * @return
    *   a new CategoryDataset with the given type parameters.
    */
  def apply[V: Numeric, Group <: Comparable[Group], Category <: Comparable[Category]](
      vgcs: Iterable[(V, Group, Category)]
  ): CategoryDataset[V, Group, Category] = {
    val catDS = CategoryDataset.withType[V, Group, Category]()
    for ((v, r, c) <- vgcs)
      catDS.add(v, r, c)
    catDS
  }

  /** Creates a new CategoryDataset with the given type parameters.
    *
    * @return
    *   a new CategoryDataset with the given type parameters.
    */
  def withType[V: Numeric, Group <: Comparable[Group], Category <: Comparable[Category]]() =
    new CategoryDataset[V, Group, Category]

  /** Creates a new CategoryDataset with the given type parameters.
    *
    * @param vgcs
    *   the values, groups and categories of the chart.
    * @return
    *   a new CategoryDataset with the given type parameters.
    */
  def apply[V: Numeric, Group <: Comparable[Group], Category <: Comparable[Category]](
      vgcs: (V, Group, Category)*
  ): CategoryDataset[V, Group, Category] =
    apply(vgcs)
}

/** A chart that plots bars representing data in groups with different categories.
  * @param chart
  *   the JFreeChart object.
  */
class CategoryChart(val chart: JFreeChart)
    extends JFreeChart(chart.getTitle.getText, chart.getPlot)
    with CanDraw
    with HasOrientation {

  val domainMargin = 0.8

  /** Configures color, outline color, and stroke width for a group.
    *
    * @param group
    *   the group.
    * @param color
    *   the color.
    * @param outlineColor
    *   the outline color.
    * @param strokeWidth
    */
  def config(
      group: Comparable[_],
      color: Color = null,
      outlineColor: Color = null,
      strokeWidth: Double = -1.0
  ): Unit = {
    val plot = chart.getCategoryPlot
    val numSeries = plot.getDataset.getRowIndex(group)
    config(numSeries, color, outlineColor, strokeWidth)
  }

  def config(
      numSeries: Int,
      color: Color,
      outlineColor: Color,
      strokeWidth: Double
  ): Unit = {
    val renderer = chart.getCategoryPlot.getRenderer().asInstanceOf[BarRenderer]

    renderer.setDrawBarOutline(true)
    if (color != null)
      renderer.setSeriesPaint(numSeries, color)
    if (outlineColor != null) {
      renderer.setDrawBarOutline(true)
      renderer.setSeriesOutlinePaint(numSeries, outlineColor)
    }
    if (strokeWidth >= 0)
      renderer.setSeriesOutlineStroke(
        numSeries,
        new BasicStroke(strokeWidth.toFloat)
      )
  }

  def config(strokeWidth: Double): Unit = {
    val renderer = chart.getCategoryPlot.getRenderer().asInstanceOf[BarRenderer]

    if (strokeWidth >= 0)
      for (i <- 0 until chart.getXYPlot.getDataset.getSeriesCount)
        renderer.setSeriesOutlineStroke(i, new BasicStroke(strokeWidth.toFloat))
  }

  def setRangeAxis(
      yMin: Double,
      yMax: Double,
      tickUnit: Double = -1.0,
      log: Boolean = false
  ): Unit = {
    val plot = chart.getCategoryPlot
    val label = plot.getRangeAxis().getLabel
    val tickFont = plot.getRangeAxis().getTickLabelFont
    val labelFont = plot.getRangeAxis().getLabelFont
    val axis =
      if (log) new LogarithmicAxis(label)
      else new NumberAxis(label)

    val margin = 0
    axis.setRange(yMin - margin, yMax + margin)
    if (!log && tickUnit > 0)
      axis.setTickUnit(new NumberTickUnit(tickUnit))
    axis.setTickLabelFont(tickFont)
    axis.setLabelFont(labelFont)

    plot.setRangeAxis(axis)
  }

  def rangeAxisLog(): Unit = {
    val plot = chart.getCategoryPlot
    val label = plot.getRangeAxis().getLabel
    val tickFont = plot.getRangeAxis().getTickLabelFont
    val labelFont = plot.getRangeAxis().getLabelFont
    val axis = new LogAxis(label)

    axis.setTickLabelFont(tickFont)
    axis.setLabelFont(labelFont)

    plot.setRangeAxis(axis)
  }
}

object CategoryChart {

  /** Creates a new CategoryChart with the given parameters.
    *
    * @param title
    *   the title of the chart.
    * @param domainLabel
    *   the label of the domain axis.
    * @param rangeLabel
    *   the label of the range axis.
    * @param data
    *   the data to be displayed.
    * @param vertical
    *   whether the bars should be vertical or horizontal.
    * @return
    *   a new CategoryChart with the given parameters.
    */
  def apply(
      title: String,
      domainLabel: String,
      rangeLabel: String,
      data: CategoryDataset[_, _, _],
      vertical: Boolean = true
  ): CategoryChart = {
    val chart = ChartFactory.createBarChart(
      title,
      domainLabel,
      rangeLabel,
      data,
      if (vertical) PlotOrientation.VERTICAL else PlotOrientation.HORIZONTAL,
      true,
      false,
      false
    )
    PlotUtils.setTheme(chart)
    PlotUtils.setBarStyle(chart)
    new CategoryChart(chart)
  }
}
