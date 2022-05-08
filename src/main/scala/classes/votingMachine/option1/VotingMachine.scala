/*
  Pepe Gallardo, 2022
 */

package classes.votingMachine.option1

class VotingMachine:
  var votes: Int = 0

  override def toString: String =
    s"VotingMachine($votes)"


@main def cheatingTest(): Unit =
  val m = VotingMachine()
  m.votes += 1
  m.votes += 1

  m.votes -= 16022
  println(m)