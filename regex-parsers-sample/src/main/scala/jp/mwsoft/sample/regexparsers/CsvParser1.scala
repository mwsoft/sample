package jp.mwsoft.sample.regexparsers

import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.input.CharSequenceReader

object CsvParser1 extends App {

  val result = new CsvParser1().parse(
    """ab,cd,ef
gh,ij,
kl,mn,op""" )
  println( result.get )

}

class CsvParser1 extends RegexParsers {
  override val skipWhitespace = false

  // 一行用の表現
  def fields = repsep( field, comma )
  def field = "[^,\r\n]*".r
  def comma = ","

  // 複数行用の表現
  def lines = repsep( fields, eol )
  def eol = "\n"

  def parse( input: String ) = parseAll( lines, input )
}
