package org.specs2.internal.scalaz

////
/**
 *
 */
////
trait Each[F[_]]  { self =>
  ////
  def each[A](fa: F[A])(f: A => Unit)

  // derived functions

  ////
  val eachSyntax = new org.specs2.internal.scalaz.syntax.EachSyntax[F] { def F = Each.this }
}

object Each {
  @inline def apply[F[_]](implicit F: Each[F]): Each[F] = F

  ////

  ////
}

