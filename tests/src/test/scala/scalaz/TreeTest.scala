package org.specs2.internal.scalaz

import std.AllInstances._
import org.specs2.internal.scalaz.scalacheck.ScalazProperties._
import org.specs2.internal.scalaz.scalacheck.ScalazArbitrary._

class TreeTest extends Spec {

  checkAll("Tree", equal.laws[Tree[Int]])

  {
    implicit def treeEqual[A: Equal]: Equal[Tree[A]] = new Equal[Tree[A]] {
      import std.stream.streamEqual
      def streamEqualApprox = streamEqual[Tree[A]].contramap((_: Stream[Tree[A]]).take(1000))
      def equal(a1: Tree[A], a2: Tree[A]) =
        Equal[A].equal(a1.rootLabel, a2.rootLabel) && streamEqualApprox.equal(a1.subForest, a2.subForest)
    }

    // TODO checkAll("Tree", traverse.laws[Tree])
    checkAll("Tree", applicative.laws[Tree])
    checkAll("Tree", comonad.laws[Tree])
  }
}
