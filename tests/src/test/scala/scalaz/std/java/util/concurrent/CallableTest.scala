package org.specs2.internal.scalaz
package std
package java
package util
package concurrent

import _root_.java.util.concurrent.Callable
import std.AllInstances._
import org.specs2.internal.scalaz.scalacheck.ScalazProperties._
import org.specs2.internal.scalaz.scalacheck.ScalazArbitrary._

class CallableTest extends Spec {
  checkAll("Callable", equal.laws[Callable[Int]])
}
