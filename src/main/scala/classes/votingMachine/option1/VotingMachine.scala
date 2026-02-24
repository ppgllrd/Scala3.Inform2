/*
  Pepe Gallardo, 2023
 */

package classes.votingMachine.option1

/**
 * A voting machine with a public `votes` field.
 * WARNING: this design is vulnerable — anyone can directly modify the vote count.
 */
class VotingMachine:
  var votes: Int = 0

  override def toString: String =
    s"VotingMachine($votes)"

@main def cheatingTest(): Unit =
  val m = VotingMachine()
  m.votes += 1
  m.votes += 1

  m.votes -= 16022 // cheating! public field allows arbitrary modification
  println(m)
