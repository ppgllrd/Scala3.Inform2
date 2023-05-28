/*
  Pepe Gallardo, 2023
 */

package ADT.propositions

import scala.collection.*

// a name for a variable
type Name = String

// interpretation for variable names to T or F
type Interpretation = immutable.Map[Name, Boolean]

object Proposition:
  private def bool2String(bool: Boolean): String =
    if bool then "T" else "F"

  private def interpretations(vars: Set[Name]): Set[Interpretation] =
    if vars.isEmpty then immutable.Set(immutable.Map[Name, Boolean]())
    else
      val v = vars.head
      val vs = vars.tail
      (for
        i <- interpretations(vs)
        b <- Set(false, true)
      yield i + (v -> b))

enum Proposition:
  case Const(bool: Boolean)
  case Var(name: Name)
  case Not(prop: Proposition)
  case And(prop1: Proposition, prop2: Proposition)
  case Or(prop1: Proposition, prop2: Proposition)
  case If(prop1: Proposition, prop2: Proposition)
  case Iff(prop1: Proposition, prop2: Proposition)

  override def toString: String = this match
    case Const(bool)       => Proposition.bool2String(bool)
    case Var(name)         => name
    case Not(prop)         => s"¬${prop.toString}"
    case And(prop1, prop2) => s"(${prop1.toString} ∧ ${prop2.toString})"
    case Or(prop1, prop2)  => s"(${prop1.toString} ∨ ${prop2.toString})"
    case If(prop1, prop2)  => s"(${prop1.toString} ⭢ ${prop2.toString})"
    case Iff(prop1, prop2) => s"(${prop1.toString} ⭤ ${prop2.toString})"

  def variables: immutable.Set[Name] = this match
    case Const(bool)       => immutable.Set[Name]()
    case Var(name)         => immutable.Set(name)
    case Not(prop)         => prop.variables
    case And(prop1, prop2) => prop1.variables.union(prop2.variables)
    case Or(prop1, prop2)  => prop1.variables.union(prop2.variables)
    case If(prop1, prop2)  => prop1.variables.union(prop2.variables)
    case Iff(prop1, prop2) => prop1.variables.union(prop2.variables)

  def eval(interp: Interpretation): Boolean = this match
    case Const(bool)       => bool
    case Var(name)         => interp(name)
    case Not(prop)         => !prop.eval(interp)
    case And(exp1, prop2)  => exp1.eval(interp) && prop2.eval(interp)
    case Or(exp1, prop2)   => exp1.eval(interp) || prop2.eval(interp)
    case If(prop1, prop2)  => !prop1.eval(interp) || prop2.eval(interp)
    case Iff(prop1, prop2) => prop1.eval(interp) == prop2.eval(interp)

  def isTautology: Boolean =
    val vs = this.variables
    val is = Proposition.interpretations(vs)
    is.forall(i => this.eval(i))

  def isConsistent: Boolean =
    val vs = this.variables
    val is = Proposition.interpretations(vs)
    is.exists(i => this.eval(i))

  def printEvals(): Unit =
    def bool(b: Boolean): String = if b then "T" else "F"
    val vs = this.variables
    val is = Proposition.interpretations(vs)
    for i <- is do
      for (v, b) <- i.toList.sortBy(_._1) do
        print(f"$v%s: ${Proposition.bool2String(b)}%s  ")
      println(s"   $this: ${Proposition.bool2String(eval(i))}")

@main def PropositionTest(): Unit =
  import Proposition.*

  val a = Var("a")
  val b = Var("b")
  val c = Var("c")

  val p1 = Or(Not(a), a)
  println(p1)
  println(p1.isTautology)
  p1.printEvals()
  println()

  val p2 = If(And(If(a, b), a), b)
  println(p2)
  println(p2.isTautology)
  p2.printEvals()
  println()

  val p3 = If(If(a, b), b)
  println(p3)
  println(p3.isTautology)
  println(p3.isConsistent)
  p3.printEvals()
