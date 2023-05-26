/******************************************************************************
 * Informática. Grado en Matemáticas. Universidad de Málaga
 * @ Pepe Gallardo
 *
 * Robot
 *
 *****************************************************************************/

package inform.robots

private case class Location(row: Int, column: Int) {
	override def equals(that: Any): Boolean =
		that match {
			case p: Location => p.row == this.row && p.column == this.column
			case _ => false
		}

	override def toString: String =
		s"Location($row, $column)"
}