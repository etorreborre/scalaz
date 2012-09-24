package org.specs2.internal.scalaz
package std.effect

import effect.{IO, Resource}

import java.io.InputStream

trait InputStreamInstances {
  implicit val inputStreamResource: Resource[InputStream] = new Resource[InputStream] {
    def close(r: InputStream) = IO(r.close)
  }
}

object inputStream extends InputStreamInstances
