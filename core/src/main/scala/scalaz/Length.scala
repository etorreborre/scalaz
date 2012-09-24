package org.specs2.internal.scalaz

////
/**
 *
 */
////
trait Length[F[_]]  { self =>
  ////
  def length[A](fa: F[A]): Int
  // derived functions

  ////
  val lengthSyntax = new org.specs2.internal.scalaz.syntax.LengthSyntax[F] { def F = Length.this }
}

object Length {
  @inline def apply[F[_]](implicit F: Length[F]): Length[F] = F

  ////

  ////
}

