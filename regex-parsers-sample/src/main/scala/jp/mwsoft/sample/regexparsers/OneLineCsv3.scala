package jp.mwsoft.sample.regexparsers

import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.input.CharSequenceReader

object OneLineCsv3 extends App {

  val result = new OneLineCsv3().parse( "abc,def,ghi" )
  println( result.get )

}

class OneLineCsv3 extends RegexParsers {
  def comma = ","
  def field = "[^,]*".r
  def fields = repsep( field, comma )
  def parse( input: String ) = parseAll( fields, input )
}
