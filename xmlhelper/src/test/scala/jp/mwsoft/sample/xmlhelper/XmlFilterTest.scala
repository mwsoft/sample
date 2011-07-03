package jp.mwsoft.sample.xmlhelper

import org.junit.runner.RunWith
import org.specs._
import org.specs.matcher._
import org.specs.runner.{ JUnitSuiteRunner, JUnit }
import jp.mwsoft.scala.xmlhelper.XmlFilter._
import scala.xml.NodeSeq

@RunWith(classOf[JUnitSuiteRunner])
class XmlFilterTest extends Specification with JUnit {

  val xml = <foo><bar id="a">A</bar><bar id="b">B</bar></foo>

  "attrFilterでid=aのbar要素を取得" in {
    (xml \\ "bar" attrFilter ("id", "a") toString) must_== "<bar id=\"a\">A</bar>"
  }

  "\\@でid=bのbar要素を取得" in {
    (xml \\@ ("bar", "id", "b") toString) must_== "<bar id=\"b\">B</bar>"
  }

  val html = <div><div class="foo bar">foo bar</div><div class="foo">foo</div><div><div class="bar">bar</div></div></div>

  "classFilterでclassにbarが指定されている要素を取得" in {
    val result = (html \\ "div" classFilter ("bar"))
    "要素が2つ取れている" in { result.size must_== 2 }
    "要素の1つ目はfoo bar" in { result(0).text must_== "foo bar" }
    "要素の2つ目はbar" in { result(1).text must_== "bar" }
  }

  "attrReFilterで正規表現を利用して、classに「oo」という文字列が含まれる要素を取得" in {
    val result = (html \\ "div" attrReFilter ("class", ".*oo.*"))
    "要素が2つ取れている" in { result.size must_== 2 }
    "要素の1つ目はfoo bar" in { result(0).text must_== "foo bar" }
    "要素の2つ目はfoo" in { result(1).text must_== "foo" }
  }
}
