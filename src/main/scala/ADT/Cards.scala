/*
  Pepe Gallardo, 2023
 */

package ADT

enum Color:
  case Red, Black

import Color.*

enum Suit:
  case Clubs, Diamonds, Hearts, Spades

  def color: Color = this match
    case Clubs | Spades => Black
    case Diamonds | Hearts => Red


import Suit.*

object Card:
  val allSuits: Set[Suit] = Set(Clubs, Diamonds, Hearts, Spades)
  val allValues: Set[Int] = Set.from(1 to 13)
  val numJokers: Int = 2


trait Card:
  def suits: Set[Suit]
  def values: Set[Int]


case object Joker extends Card:
  override def suits: Set[Suit] = Card.allSuits

  override def values: Set[Int] = Card.allValues


case class Pair(value: Int, suit: Suit) extends Card:
  require(Card.allValues.contains(value), s"value $value is wrong")

  override def suits: Set[Suit] = Set(suit)

  override def values: Set[Int] = Set(value)


class Deck(seed: Int):
  private val rnd = inform.util.random.Random(seed)

  private val cards: Array[Card] = init()

  private def init(): Array[Card] =
    val cs = new Array[Card](Card.allValues.size * Card.allSuits.size + Card.numJokers)
    var i = 0
    for s <- Card.allSuits do
      for v <- Card.allValues do
        cs(i) = Pair(v, s)
        i += 1
    for _ <- 0 until Card.numJokers do
      cs(i) = Joker
      i += 1
    cs

  def shuffle(): Unit =
    for i <- cards.indices do
      val j = rnd.between(i, cards.length)
      val tmp = cards(i)
      cards(i) = cards(j)
      cards(j) = tmp

  def hand(size: Int): Array[Card] =
    require(size > 0 && size <= cards.length)
    shuffle()
    cards.take(size)


object Deck:
  def isPoker(hand: Array[Card]): Boolean =
    val values = hand.flatMap(_.values)
    val counters = Array.fill(Card.allValues.size)(0)

    var found = false
    var i = 0
    while i < values.length && !found do
      val value = values(i) - 1
      counters(value) += 1
      if counters(value) >= 4 then
        found = true
      else
        i += 1

    found

