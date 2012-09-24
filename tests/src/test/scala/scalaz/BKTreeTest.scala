package org.specs2.internal.scalaz

import org.specs2.internal.scalaz.scalacheck.ScalazProperties._
import org.specs2.internal.scalaz.scalacheck.ScalazArbitrary._
import std.AllInstances._
import org.specs2.internal.scalaz.scalacheck.ScalazArbitrary._
import org.specs2.internal.scalaz.scalacheck.ScalaCheckBinding._
import org.scalacheck.{Arbitrary, Gen}


class BKTreeTest extends Spec {
  implicit val IntArb = Arbitrary[Int](Gen.choose(Int.MinValue / 4, Int.MaxValue / 4))

  "string distance" in {
    BKTree[String]("kitten").containsApproximate("sitting", 3)
  }

  "empty" ! check {
    (a: String) => !BKTree[String]().contains(a)
  }

  "singleton" ! check {
    (a: String) => BKTree[String](a).contains(a)
  }

  "contains" ! check {
    (a: String, as: Set[String]) => BKTree[String](as.toSeq: _*).contains(a) == as.contains(a)
  }

  "values" ! check {
    (as: Set[String]) => BKTree[String](as.toSeq: _*).values.toSet == as
  }

  // TODO more tests

  checkAll(functor.laws[BKTree])
  checkAll(monoid.laws[BKTree[Int]])
}