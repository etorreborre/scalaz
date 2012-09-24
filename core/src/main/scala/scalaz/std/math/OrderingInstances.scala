package org.specs2.internal.scalaz
package std
package math

import scala.math.{Ordering => SOrdering}
import org.specs2.internal.scalaz.Ordering


trait OrderingInstances {
  def orderingMonoid[A] = new Monoid[SOrdering[A]] {
    def append(f1: SOrdering[A], f2: => SOrdering[A]) = new SOrdering[A] {
      def compare(x: A, y: A): Int = f1.compare(x, y) match {
        case 0 => f2.compare(x, y)
        case o => o
      }
    }
    def zero = new SOrdering[A] {
      def compare(x: A, y: A): Int = 0
    }
  }
}

trait OrderingFunctions {
  final def ToScalazOrderFromOrdering[A](oa: SOrdering[A]): org.specs2.internal.scalaz.Order[A] = new org.specs2.internal.scalaz.Order[A] {
    def order(x: A, y: A): Ordering = Ordering.fromInt(oa.compare(x, y))
  }
}

object ordering extends OrderingInstances