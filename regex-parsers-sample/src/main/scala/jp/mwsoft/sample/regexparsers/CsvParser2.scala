package jp.mwsoft.sample.regexparsers

import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.input.CharSequenceReader

object CsvParser2 extends App {

  val result = new CsvParser2().parse( "ab,cd,ef\rgh,ij,\nkl,mn,op\r\nqr,st,uv" )
  println( result.get )

}

class CsvParser2 extends RegexParsers {
  override val skipWhitespace = false

  def fields = repsep( field, comma )
  def field = "[^,\r\n]*".r
  def comma = ","

  def lines = repsep( fields, eol )
  // 改行コード対応
  def eol = "\r\n" | "\n" | "\r"

  def parse( input: String ) = parseAll( lines, input )
}
