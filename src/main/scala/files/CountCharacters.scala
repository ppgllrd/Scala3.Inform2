/*
  Pepe Gallardo, 2023
 */

package files

import scala.collection.*
import java.io.File
import java.io.FileReader

object CountCharacters:
  def count(fileName: String, character: Char): Int =
    val file = File(fileName)
    require(file.exists, s"File $fileName does not exist")
    val fr = new FileReader(file)
    var counter = 0
    var code: Int = fr.read()
    while code >= 0 do
      if code == character then counter += 1
      code = fr.read()

    fr.close()
    counter

@main def countCharactersTest(): Unit =
  val vowels = immutable.SortedSet('a', 'e', 'i', 'o', 'u')
  for character <- vowels do
    val count = CountCharacters.count("data/files/text.txt", character)
    println(s"Character $character is included $count times in file")
