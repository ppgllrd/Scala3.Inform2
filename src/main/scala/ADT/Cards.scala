/*
  Pepe Gallardo, 2023
 */

package ADT

/** Represents the color of a card suit. */
enum Color:
  case Red, Black

import Color.*

/** Represents one of the four standard playing-card suits. */
enum Suit:
  case Clubs, Diamonds, Hearts, Spades

  /** Returns the [[Color]] of this suit (black for Clubs/Spades, red for Diamonds/Hearts). */
  def color: Color = this match
    case Clubs | Spades    => Black
    case Diamonds | Hearts => Red

import Suit.*

/** Companion object holding constants shared by all cards. */
object Card:
  val allSuits: Set[Suit] = Set(Clubs, Diamonds, Hearts, Spades)
  val allValues: Set[Int] = Set.from(1 to 13)
  val numJokers: Int = 2

/** A playing card that knows which suits and values it can represent. */
trait Card:
  def suits: Set[Suit]
  def values: Set[Int]

/** A Joker card — matches every suit and every value. */
case object Joker extends Card:
  override def suits: Set[Suit] = Card.allSuits

  override def values: Set[Int] = Card.allValues

/** A standard card with a single numeric value (1–13) and a single suit. */
case class Pair(value: Int, suit: Suit) extends Card:
  require(Card.allValues.contains(value), s"value $value is wrong")

  override def suits: Set[Suit] = Set(suit)

  override def values: Set[Int] = Set(value)

/** A 54-card deck (52 standard cards + 2 jokers) with shuffling and dealing.
 *
 *  @param seed seed for the pseudo-random number generator, for reproducibility.
 */
class Deck(seed: Int):
  private val rnd = inform.util.random.Random(seed)

  private val cards: Array[Card] = init()

  /** Populates the deck: one [[Pair]] per (value, suit) combination, plus jokers. */
  private def init(): Array[Card] =
    val cs = Array.ofDim[Card](Card.allValues.size * Card.allSuits.size + Card.numJokers)
    var i = 0
    for s <- Card.allSuits do
      for v <- Card.allValues do
        cs(i) = Pair(v, s)
        i += 1
    for _ <- 0 until Card.numJokers do
      cs(i) = Joker
      i += 1
    cs

  /** Randomly permutes the deck in-place using Fisher–Yates shuffle. */
  def shuffle(): Unit =
    for i <- cards.indices do
      val j = rnd.between(i, cards.length) // random index in [i, length)
      val tmp = cards(i)
      cards(i) = cards(j)
      cards(j) = tmp

  /** Shuffles the deck and deals the first `size` cards. */
  def hand(size: Int): Array[Card] =
    require(size > 0 && size <= cards.length)
    shuffle()
    cards.take(size)

object Deck:
  /** Returns `true` if a 5-card hand contains four-of-a-kind (poker). */
  def isPoker(hand: Array[Card]): Boolean =
    require(hand.length == 5)
    // Jokers contribute all values via flatMap, so they count toward any group
    val values = hand.flatMap(_.values)
    val counters = Array.fill(Card.allValues.size)(0)

    var found = false
    var i = 0
    while i < values.length && !found do
      val value = values(i) - 1
      counters(value) += 1
      if counters(value) >= 4 then found = true
      else i += 1

    found

@main def testCards(): Unit =
  val deck = Deck(0)

  val numExperiments = 1000000
  var successes = 0
  for _ <- 0 until numExperiments do
    val hand = deck.hand(5)
    if Deck.isPoker(hand) then successes += 1
  val prob = successes.toDouble / numExperiments

  println(s"Probability of poker: $prob")
