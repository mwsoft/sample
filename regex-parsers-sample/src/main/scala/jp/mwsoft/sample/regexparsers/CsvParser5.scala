package jp.mwsoft.sample.regexparsers

import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.input.CharSequenceReader

object CsvParser5 extends App {

  val result = new CsvParser5().parse( """abc,def,"ghi"
"jkl","mno",pqr
"stu",vwx,yz""" )
  println( result.get )

}

class CsvParser5 extends RegexParsers {
  override val skipWhitespace = false

  // コーテーション無しフィールド
  def normalField = "[^,\"\r\n]*".r

  // コーテーション有りフィールド
  def quoteField = dblquote ~> "[^,\"\r\n]*".r <~ dblquote
  def dblquote = "\""

  // 一行の表現
  def fields = repsep( quoteField | normalField, "," )

  // 複数行の表現
  def lines = repsep( fields | fields, eol )
  def eol = "\r\n" | "\n" | "\r"

  def parse( input: String ) = parseAll( lines, input )
}
