package org.specs2.internal.scalaz

import std.AllInstances._
import org.specs2.internal.scalaz.scalacheck.ScalazProperties._
import org.specs2.internal.scalaz.scalacheck.ScalazArbitrary._

class CaseInsensitiveTest extends Spec {

  "map identity" ! check {
    (a: CaseInsensitive[String]) =>
      Equal[CaseInsensitive[String]].equal(a.map(x => x), a)
  }
  
  "map associativity" ! check {
    (a: CaseInsensitive[String], f: String => String, g: String => String) =>
      Equal[CaseInsensitive[String]].equal(a.map(f).map(g), a.map(g compose f))
  }
  
  checkAll(monoid.laws[CaseInsensitive[String]])
  checkAll(equal.laws[CaseInsensitive[String]])
  checkAll(order.laws[CaseInsensitive[String]])
}