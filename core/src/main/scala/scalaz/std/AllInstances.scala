package org.specs2.internal.scalaz.std

trait AllInstances
  extends AnyValInstances with FunctionInstances with ListInstances with MapInstances
  with OptionInstances with SetInstances with StringInstances with StreamInstances
  with TupleInstances with VectorInstances
  with EitherInstances with PartialFunctionInstances with TypeConstraintInstances
  with org.specs2.internal.scalaz.std.math.BigDecimalInstances with org.specs2.internal.scalaz.std.math.BigInts
  with org.specs2.internal.scalaz.std.math.OrderingInstances
  with org.specs2.internal.scalaz.std.util.parsing.combinator.Parsers
  with org.specs2.internal.scalaz.std.java.util.MapInstances
  with org.specs2.internal.scalaz.std.java.math.BigIntegerInstances
  with org.specs2.internal.scalaz.std.java.util.concurrent.CallableInstances
  with NodeSeqInstances
  // Intentionally omitted: IterableInstances

object AllInstances extends AllInstances
