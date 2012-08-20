package jp.mwsoft.sample.regexparsers

import scala.util.parsing.combinator.RegexParsers

object OneLineCsv1 extends App {
  val result = new OneLineCsv1().parse("""abc,def,ghi,""")
  println(result.get)
}

class OneLineCsv1 extends RegexParsers {
  def comma = ","
  def field = ("[^,]*".r <~ comma).*
  def parse(input: String) = parseAll(field, input)
}
