package org.specs2.internal.scalaz
package std

import org.specs2.internal.scalaz.scalacheck.ScalazProperties._
import org.specs2.internal.scalaz.scalacheck.ScalazArbitrary._
import std.AllInstances._

class IterableTest extends Spec {

  import std.iterable._

  checkAll(order.laws[Iterable[Boolean]].withProp("benchmark", order.scalaOrdering[Iterable[Boolean]]))
}