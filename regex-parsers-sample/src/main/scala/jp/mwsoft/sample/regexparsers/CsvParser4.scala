package jp.mwsoft.sample.regexparsers

import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.input.CharSequenceReader

object CsvParser4 extends App {

  val result = new CsvParser4().parse( """"abc","def","ghi"
"jkl","mno","pqr"
"stu","vwx","yz"""" )
  println( result.get )

}

class CsvParser4 extends RegexParsers {
  override val skipWhitespace = false

  // 一行の表現
  def fields = repsep( field, "," )
  def field = dblquote ~> text <~ dblquote
  def dblquote = "\""
  def text = "[^,\"\r\n]*".r

  // 複数行の表現
  def lines = repsep( fields, eol )
  def eol = "\r\n" | "\n" | "\r"

  def parse( input: String ) = parseAll( lines, input )
}
