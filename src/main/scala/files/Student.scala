/*
  Pepe Gallardo, 2023
 */

package files

import java.io.{File, PrintStream}
import java.util.Scanner
import scala.collection.*

case class Student(name: String, surname: String, marks: List[Double])

object Report:
  def readMarks(fileName: String): List[Student] =
    val file = File(fileName)
    require(file.exists, s"file $fileName does not exist")

    val students = mutable.ArrayBuffer[Student]()
    val scFile = Scanner(file)
    while scFile.hasNextLine do
      val line = scFile.nextLine()

      val scLine = Scanner(line)
      val name = scLine.next()
      val surname = scLine.next()
      val marks = mutable.ArrayBuffer[Double]()
      while scLine.hasNextDouble do marks += scLine.nextDouble()
      scLine.close()

      val student = Student(name, surname, marks.toList)
      students += student

    scFile.close()
    students.toList

  def writeReport(fileName: String, students: List[Student]): Unit =
    val file = File(fileName)
    val ps = PrintStream(file)
    val line = "======================================"
    ps.println(line)
    ps.println(" Student's marks")
    ps.println(line)
    for Student(name, surname, marks) <- students.sortBy(_.surname) do
      val average = if marks.isEmpty then 0.0 else marks.sum.toDouble / marks.length
      ps.println(f" $surname%-15s $name%-15s $average%4.2f")
    ps.println(line)
    ps.close()

@main def testStudents(): Unit =
  // Using . instead of , as separator for decimals in doubles
  import java.util.Locale
  Locale.setDefault(Locale.ENGLISH)

  val students = Report.readMarks("data/files/students.txt")
  Report.writeReport("data/files/report.txt", students)
