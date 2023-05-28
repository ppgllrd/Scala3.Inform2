/*
  Pepe Gallardo, 2023
 */

package ADT.lists.covariant

import scala.annotation.tailrec

object List:
  def empty[A]: List[A] = List.Empty

enum List[+A]:
  case Empty extends List[Nothing]
  case Cons(hd: A, tl: List[A]) extends List[A]

  def head: A = this match
    case Empty      => sys.error("head of empty list")
    case Cons(x, _) => x

  def tail: List[A] = this match
    case Empty       => sys.error("tail of empty list")
    case Cons(_, xs) => xs

  def ::[B >: A](x: B): List[B] = Cons(x, this)

  def length: Int = this match
    case Empty       => 0
    case Cons(_, xs) => 1 + xs.length

  def ++[B >: A](that: List[B]): List[B] = this match
    case Empty       => that
    case Cons(x, xs) => Cons(x, xs ++ that)

  def map[B](f: A => B): List[B] = this match
    case Empty       => Empty
    case Cons(x, xs) => Cons(f(x), xs.map(f))

  def filter(p: A => Boolean): List[A] = this match
    case Empty       => Empty
    case Cons(x, xs) => if p(x) then Cons(x, xs.filter(p)) else xs.filter(p)

  def foldLeft[B](z: B)(f: (B, A) => B): B =
    @tailrec
    def aux(ac: B, xs: List[A]): B = xs match
      case Empty       => ac
      case Cons(y, ys) => aux(f(ac, y), ys)

    aux(z, this)

  def reverse: List[A] =
    foldLeft(Empty: List[A])((xs, x) => Cons(x, xs))

@main def testLists(): Unit =
  import List.*

  val xs: List[Int] = Cons(1, Cons(2, Cons(3, Empty)))

  val ys: List[Int] = 1 :: 2 :: 3 :: Empty

  println(ys.map(x => x * 2).filter(x => x > 2))

  println(ys.reverse)
