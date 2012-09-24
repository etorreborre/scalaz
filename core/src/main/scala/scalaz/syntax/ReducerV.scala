package org.specs2.internal.scalaz
package syntax

trait ReducerV[A] extends Ops[A] {
  /** Convert the value into a monoid */
  def unit[M](implicit r: Reducer[A,M]): M = r.unit(self)

  /** Append the value to a monoid for use in left-to-right reduction */
  def snoc[C](c: C)(implicit r: Reducer[C,A]): A = r.snoc(self, c)

  /** Prepend the value to a monoid for use in right-to-left reduction */
  def cons[M](m: M)(implicit r: Reducer[A,M]): M = r.cons(self, m)
}

trait ToReducerOps {
  implicit def ToReducerV[A](a: A) = new ReducerV[A]{ def self = a }
}