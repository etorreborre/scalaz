package org.specs2.internal.scalaz.example
package xml

object SimpleTokens {
  val in = """<html lang="en"><head><div><div>wibble</div></div></head><body>simple</body><w:wibble><div></div></w:wibble></html>"""
  import org.specs2.internal.scalaz.xml.Xml._

  def main(args: Array[String]) {
    val r = in.parseXml
    r foreach (_.xprint(pretty.shortEmptyTagOff))
  }
}