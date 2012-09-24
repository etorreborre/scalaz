package org.specs2.internal.scalaz

////
/**
 *
 */
////
trait ArrId[=>:[_, _]]  { self =>
  ////
  def id[A]: A =>: A

  ////
  val arrIdSyntax = new org.specs2.internal.scalaz.syntax.ArrIdSyntax[=>:] { def F = ArrId.this }
}

object ArrId {
  @inline def apply[F[_, _]](implicit F: ArrId[F]): ArrId[F] = F

  ////

  ////
}

