/*
  Pepe Gallardo, 2023
 */

package files

import scala.collection.*
import java.nio.file.{Path, Files}

object CountCharacters:
  def count(fileName: String, character: Char): Int =
    val path = Path.of(fileName)
    require(Files.exists(path), s"File $fileName does not exist")
    val br = Files.newBufferedReader(path)
    var counter = 0
    var code: Int = br.read()
    while code >= 0 do
      if code == character then counter += 1
      code = br.read()

    br.close()
    counter

@main def countCharactersTest(): Unit =
  val vowels = immutable.SortedSet('a', 'e', 'i', 'o', 'u')
  for character <- vowels do
    val count = CountCharacters.count("data/files/text.txt", character)
    println(s"Character $character is included $count times in file")
