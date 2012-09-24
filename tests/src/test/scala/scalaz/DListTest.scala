package org.specs2.internal.scalaz

import std.AllInstances._
import org.specs2.internal.scalaz.scalacheck.ScalazProperties._
import org.specs2.internal.scalaz.scalacheck.ScalazArbitrary._

class DListTest extends Spec {
  checkAll(equal.laws[DList[Int]])
  checkAll(monoid.laws[DList[Int]])
  checkAll(monad.laws[DList])
}
