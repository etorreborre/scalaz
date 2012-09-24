package org.specs2.internal.scalaz
package std

import std.AllInstances._
import org.specs2.internal.scalaz.scalacheck.ScalazProperties._

class StringTest extends Spec {
  checkAll(monoid.laws[String])

  checkAll(order.laws[String].withProp("benchmark", order.scalaOrdering[String]))
}
