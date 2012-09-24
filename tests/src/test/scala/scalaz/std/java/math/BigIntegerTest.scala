package org.specs2.internal.scalaz
package std
package java
package math

import _root_.java.math.BigInteger
import std.AllInstances._
import org.specs2.internal.scalaz.scalacheck.ScalazProperties._
import org.specs2.internal.scalaz.scalacheck.ScalazArbitrary._
import Tags._

class BigIntegerTest extends Spec {
  checkAll("BigInteger", order.laws[BigInteger])
  checkAll("BigInteger @@ Multiplication", order.laws[BigInteger @@ Multiplication])
  checkAll("BigInteger", group.laws[BigInteger])
  checkAll("BigInteger", monoid.laws[BigInteger])
  checkAll("BigInteger @@ Multiplication", monoid.laws[BigInteger @@ Multiplication])
}
