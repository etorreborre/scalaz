package org.specs2.internal.scalaz
package effect

import ST._

/**
 * A mutable reference in the IO monad. Note that unsafePerformIO will allow leaking
 * such a reference out of the monad, but any operations on a leaked reference are still monadic.
 */
sealed trait IORef[A] {
  val value: STRef[RealWorld, A]

  def read: IO[A] = STToIO(value.read)

  def write(a: => A): IO[Unit] = STToIO(value.write(a) map (_ => ()))

  def mod(f: A => A): IO[A] = STToIO(value.mod(f) flatMap (_.read))
}

object IORef extends IORefs

trait IORefs {
  private[effect] def ioRef[A](v: STRef[RealWorld, A]): IORef[A] = new IORef[A] {
    val value = v
  }
}