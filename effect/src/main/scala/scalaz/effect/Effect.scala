package org.specs2.internal.scalaz
package effect

object Effect extends Effects

trait Effects
  extends syntax.effect.ToAllEffectTypeClassOps
  with std.effect.AllEffectInstances
