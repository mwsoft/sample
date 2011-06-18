package jp.mwsoft.scala.xmlhelper

import org.junit.runner.RunWith
import org.specs._
import org.specs.matcher._
import org.specs.runner.{ JUnitSuiteRunner, JUnit }
import jp.mwsoft.scala.xmlhelper.XmlFilter._

@RunWith(classOf[JUnitSuiteRunner])
class XmlFilterTest extends Specification with JUnit {

  println(1, <foo><bar id="a">A</bar><bar id="b">B</bar></foo> \\ "bar")

  println(2, <foo><bar id="a">A</bar><bar id="b">B</bar></foo> \\ "bar" attrFilter ("id", "a"))

  println(3, <foo><bar id="a">A</bar><bar id="b">B</bar></foo> \\@ ("bar", "id", "a"))

  println(4, <foo><bar class="a1 b1">A</bar><bar class="a2 b2">B</bar></foo> \\ "bar" classFilter "a1")
}
