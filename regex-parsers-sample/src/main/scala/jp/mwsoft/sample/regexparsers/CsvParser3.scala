package jp.mwsoft.sample.regexparsers

import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.input.CharSequenceReader

object CsvParser3 extends App {

  val result = new CsvParser3().parse( """"abc","def","ghi"""" )
  println( result.get )

}

class CsvParser3 extends RegexParsers {
  override val skipWhitespace = false

  // ダブルコーテーションで囲まれた感じの表現
  def fields = repsep( field, "," )
  def field = dblquote ~> text <~ dblquote
  def dblquote = "\""
  def text = "[^,\"\r\n]*".r

  def parse( input: String ) = parseAll( fields, input )
}
