package org.specs2.internal.scalaz.example
package xml

object ParsePrintFile {
  import org.specs2.internal.scalaz._, Scalaz._, xml.Xml._

  def main(args: Array[String]) {
    val r = args(0).parseXmlFile
    r.println
  }
}