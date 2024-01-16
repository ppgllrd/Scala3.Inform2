/*
  Pepe Gallardo, 2023
 */

package files

import java.nio.file.{Path, Files}
import java.util.Scanner
import scala.collection.*

case class Student(name: String, surname: String, marks: List[Double])

object Report:
  def readMarks(fileName: String): List[Student] =
    val path = Path.of(fileName)
    require(Files.exists(path), s"File $fileName does not exist")

    val students = mutable.ArrayBuffer[Student]()
    val scFile = Scanner(path)
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
    val path = Path.of(fileName)
    val bw = Files.newBufferedWriter(path)
    val line = "======================================"
    bw.write(line)
    bw.newLine()
    bw.write(" Student's marks")
    bw.newLine()
    bw.write(line)
    bw.newLine()
    for Student(name, surname, marks) <- students.sortBy(_.surname) do
      val average = if marks.isEmpty then 0.0 else marks.sum / marks.length
      bw.write(f" $surname%-15s $name%-15s $average%4.2f")
      bw.newLine()
    bw.write(line)
    bw.newLine()
    bw.close()

@main def testStudents(): Unit =
  // Using . instead of , as separator for decimals in doubles
  import java.util.Locale
  Locale.setDefault(Locale.ENGLISH)

  val students = Report.readMarks("data/files/students.txt")
  Report.writeReport("data/files/report.txt", students)
