package org.specs2.internal.scalaz
package effect

////
/**
 *
 */
////
trait LiftIO[F[_]]  { self =>
  ////

  def liftIO[A](ioa: IO[A]): F[A]

  // derived functions

  ////
  val liftIOSyntax = new org.specs2.internal.scalaz.syntax.effect.LiftIOSyntax[F] { def F = LiftIO.this }
}

object LiftIO {
  @inline def apply[F[_]](implicit F: LiftIO[F]): LiftIO[F] = F

  ////
  
  ////
}

