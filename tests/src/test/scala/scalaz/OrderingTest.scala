package org.specs2.internal.scalaz

import org.specs2.internal.scalaz.scalacheck.ScalazProperties._
import org.specs2.internal.scalaz.scalacheck.ScalazArbitrary._

class OrderingTest extends Spec {
  checkAll("Ordering", enum.laws[Ordering])
}
