package org.specs2.internal.scalaz
package effect

////
/**
 *
 */
////
trait MonadControlIO[F[_]] extends LiftControlIO[F] with Monad[F] { self =>
  ////

  // derived functions

  ////
  val monadControlIOSyntax = new org.specs2.internal.scalaz.syntax.effect.MonadControlIOSyntax[F] { def F = MonadControlIO.this }
}

object MonadControlIO {
  @inline def apply[F[_]](implicit F: MonadControlIO[F]): MonadControlIO[F] = F

  ////

  ////
}

