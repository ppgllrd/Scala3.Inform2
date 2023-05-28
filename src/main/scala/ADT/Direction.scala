/*
  Pepe Gallardo, 2023
 */

package ADT

import ADT.Direction.{East, North, West}

enum Direction:
  case North, West, South, East

  def turnLeft: Direction = this match
    case North => West
    case West  => South
    case South => East
    case East  => North

  def turnRight: Direction = this match
    case North => East
    case West  => North
    case South => West
    case East  => South

@main def testDirections(): Unit =
  val ds: List[Direction] = List(Direction.North, Direction.West, Direction.South, Direction.East)

  for d <- ds do println(s"If you turn 90 degrees left from $d you get to ${d.turnLeft}")
