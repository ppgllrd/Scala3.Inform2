/*
  Pepe Gallardo, 2023
 */

package files

import scala.collection.*
import java.nio.file.{Path, Files}

/** Demonstrates reading a text file character by character using a `BufferedReader`. */
object CountCharacters:
  /** Counts how many times `character` appears in the file at `fileName`.
   *
   *  @param fileName path to the text file to read.
   *  @param character the character to search for.
   *  @return the number of occurrences of `character` in the file.
   */
  def count(fileName: String, character: Char): Int =
    val path = Path.of(fileName)
    require(Files.exists(path), s"File $fileName does not exist")
    val br = Files.newBufferedReader(path)
    var counter = 0
    var code: Int = br.read() // read() returns -1 at end of file
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
