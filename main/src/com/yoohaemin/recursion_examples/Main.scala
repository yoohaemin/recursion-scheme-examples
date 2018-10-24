package com.yoohaemin.recursion_examples

import cats.Functor
import cats.syntax.all._
import cats.data.Cokleisli
import qq.droste.Algebra

object Main extends App {

  println("Hello World!")

}

object Cata {

  sealed trait ListF[A]
  case class NilF[A]() extends ListF[A]
  case class ConsF[A](head: Int, a: A) extends ListF[A]

  implicit val listFFunctor: Functor[ListF] = new Functor[ListF] {
    override def map[A, B](fa: ListF[A])(f: A => B): ListF[B] = fa match {
      case NilF() => NilF()
      case ConsF(head, a) => ConsF(head, f(a))
    }
  }

  type FAlgebra[F[_], A] = Cokleisli[F, A, A]

  def cata[F[_]: Functor, R, A](
      algebra: FAlgebra[F, A],
      out: R => F[R]
  )(
      r: R
  ): A =
    algebra.run(
      out(r).map(cata(algebra, out))
    )

}
