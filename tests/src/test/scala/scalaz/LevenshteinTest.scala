package org.specs2.internal.scalaz

import org.specs2.internal.scalaz.scalacheck.ScalazProperties._
import org.specs2.internal.scalaz.scalacheck.ScalazArbitrary
import org.specs2.internal.scalaz.scalacheck.ScalazArbitrary.{stateTArb => _, _}
import std.AllInstances._

class LevenshteinTest extends Spec {
  "string distance" in {
    MetricSpace[String].distance("kitten", "sitting") must be_===(3)
  }

  checkAll(metricSpace.laws[String])
}