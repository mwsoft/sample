package jp.mwsoft.sample.regexparsers

import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.input.CharSequenceReader

object CsvParser6 extends App {

  val result = new CsvParser6().parse( """abc,de"f,"gh
i"
"jk,l","m""no",pqr
"stu",vwx,yz""" )
  println( result.get )

}

class CsvParser6 extends RegexParsers {
  override val skipWhitespace = false

  // コーテーション無しフィールド
  def normalField = "[^,\r\n]*".r

  // コーテーション有りフィールド
  def quoteField = dblquote ~> ( ( "[^\"]".r | escDblquote ).* ^^ ( x => x.mkString ) ) <~ dblquote
  def dblquote = "\""
  def escDblquote = "\"\"" ^^ ( x => "\"" )

  // 一行の表現
  def fields = repsep( quoteField | normalField, "," )

  // 複数行の表現
  def lines = repsep( fields | fields, eol )
  def eol = "\r\n" | "\n" | "\r"

  def parse( input: String ) = parseAll( lines, input )
}
