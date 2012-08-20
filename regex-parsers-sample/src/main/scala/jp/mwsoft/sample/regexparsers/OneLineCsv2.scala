package jp.mwsoft.sample.regexparsers

import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.input.CharSequenceReader

object OneLineCsv2 extends App {

  val result = new OneLineCsv2().parse( "abc,def,ghi" )
  println( result.get )

}

class OneLineCsv2 extends RegexParsers {
  def comma = ","
  def field = "[^,]*".r
  def fields = field ~ ( ( comma ~> field ).* ) ^^ { case first ~ other => first :: other }
  def parse( input: String ) = parseAll( fields, input )
}
