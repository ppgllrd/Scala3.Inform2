package robot

import inform.robot.*

@main def robotTest(): Unit =
  def line(robot: Robot, len: Int): Unit =
    for i <- 0 until len do
      if !robot.isOnDark then
        robot.darken()
      robot.forward()

  def square(robot: Robot, len: Int): Unit =
    for i <- 0 until 4 do
      line(robot, len)
      robot.right()


  val robot = Robot("data/maps/map27x15.txt")
  robot.setDelay(125)
  square(robot, 6)