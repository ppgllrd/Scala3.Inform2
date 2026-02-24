/*
  Pepe Gallardo, 2023
 */

package classes.votingMachine.option2

/**
 * A voting machine with proper encapsulation.
 * The private `votes` field can only be incremented through the `oneVote` method.
 */
class VotingMachine:
  private var votes: Int = 0

  def oneVote(): Unit =
    votes += 1

  def totalVotes: Int =
    votes

  override def toString: String =
    s"VotingMachine($votes)"

@main def noCheatingTest(): Unit =
  val m = VotingMachine()
  m.oneVote()
  m.oneVote()

  // m.votes -= 16022  // won't compile as votes is private
  println(m)
