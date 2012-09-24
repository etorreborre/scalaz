package org.specs2.internal.scalaz

import std.AllInstances._
import org.specs2.internal.scalaz.scalacheck.ScalazProperties._
import org.specs2.internal.scalaz.scalacheck.ScalazArbitrary._

class NeedTest extends Spec {
  checkAll("Value", monad.laws[Value])
  checkAll("Name", monad.laws[Name])
  checkAll("Need", monad.laws[Need])
}
