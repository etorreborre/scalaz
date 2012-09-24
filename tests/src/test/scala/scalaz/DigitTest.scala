package org.specs2.internal.scalaz

import std.AllInstances._
import org.specs2.internal.scalaz.scalacheck.ScalazProperties._
import org.specs2.internal.scalaz.scalacheck.ScalazArbitrary._
import org.specs2.internal.scalaz.scalacheck.ScalaCheckBinding._
import org.scalacheck.{Gen, Arbitrary}

class DigitTest extends Spec {
  checkAll(order.laws[Digit])
}
