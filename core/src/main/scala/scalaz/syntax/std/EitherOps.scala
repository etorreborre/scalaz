package org.specs2.internal.scalaz
package syntax
package std

import org.specs2.internal.scalaz.std.{either => e}

trait EitherOps[A, B] extends Ops[Either[A, B]] {

  final def disjunction: A \/ B = \/ fromEither self
}

trait ToEitherOps {
  implicit def ToEitherOpsFromEither[A, B](e: Either[A, B]): EitherOps[A, B] = new EitherOps[A, B] {
    val self = e
  }
}

