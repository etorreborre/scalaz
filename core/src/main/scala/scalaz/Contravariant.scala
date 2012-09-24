package org.specs2.internal.scalaz

////
/**
 *
 */
////
trait Contravariant[F[_]]  { self =>
  ////
  def contramap[A, B](r: F[A])(f: B => A): F[B]

  // derived functions

  ////
  val contravariantSyntax = new org.specs2.internal.scalaz.syntax.ContravariantSyntax[F] { def F = Contravariant.this }
}

object Contravariant {
  @inline def apply[F[_]](implicit F: Contravariant[F]): Contravariant[F] = F

  ////

  ////
}

