/*
  Pepe Gallardo, 2023
 */

package robot

import inform.robot.*

@main def robotTest(): Unit =
  def line(robot: Robot, len: Int): Unit =
    for i <- 0 until len do
      if !robot.isOnDark then robot.darken()
      robot.forward()

  def square(robot: Robot, len: Int): Unit =
    for i <- 0 until 4 do
      line(robot, len)
      robot.right()

  val robot = Robot.inMap("data/maps/map27x15.txt")
  robot.setDelay(125)
  square(robot, 6)

@main def robotTest2(): Unit =
  extension (robot: Robot)
    def line(len: Int): Unit =
      for i <- 0 until len do
        if !robot.isOnDark then robot.darken()
        robot.forward()

    def square(len: Int): Unit =
      for i <- 0 until 4 do
        robot.line(len)
        robot.right()

  val robot = Robot.inMap("data/maps/map27x15.txt")
  robot.setDelay(125)
  robot.square(6)

@main def robotTest3(): Unit =
  extension (robot: Robot)
    def line(): Unit =
      while robot.canMoveForward do
        if robot.isOnDark then robot.lighten()
        robot.forward()

    def path(): Unit =
      for i <- 0 until 4 do
        robot.line()
        robot.left()

  val robot = Robot.inMap("data/maps/mapSquare.txt")
  robot.setDelay(125)
  robot.path()