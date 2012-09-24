package org.specs2.internal.scalaz

import std.AllInstances._
import org.specs2.internal.scalaz.scalacheck.ScalazProperties._
import org.specs2.internal.scalaz.scalacheck.ScalazArbitrary._
import Id._

class IdTest extends Spec {
  checkAll(monad.laws[Id])
  checkAll(traverse.laws[Id])
}
