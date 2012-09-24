package org.specs2.internal.scalaz
package std
package math

import std.AllInstances._
import org.specs2.internal.scalaz.scalacheck.ScalazProperties._
import org.specs2.internal.scalaz.scalacheck.ScalazArbitrary._
import Tags._

class BigIntTest extends Spec {
  checkAll("BigInt", order.laws[BigInt])
  checkAll("BigInt @@ Multiplication", order.laws[BigInt @@ Multiplication])

  checkAll("BigInt @@ Multiplication", monoid.laws[BigInt @@ Multiplication])
  checkAll("BigInt", group.laws[BigInt])
}
