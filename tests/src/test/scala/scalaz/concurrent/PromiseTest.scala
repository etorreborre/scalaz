package org.specs2.internal.scalaz
package concurrent

import org.specs2.internal.scalaz.scalacheck.ScalazProperties._
import org.specs2.internal.scalaz.scalacheck.ScalazArbitrary._
import std.AllInstances._

class PromiseTest extends Spec {
  implicit def promiseEqual[A: Equal] = Equal[A].contramap((_: Promise[A]).get)

  checkAll(monad.laws[Promise])
  checkAll(traverse.laws[Promise])
  checkAll(comonad.laws[Promise])
}