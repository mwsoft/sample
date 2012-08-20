package jp.mwsoft.sample.regexparsers

import scala.util.parsing.combinator.RegexParsers

object SimpleRegex1 extends App {

  val result = new SimpleRegex1().parse( "abc" )
  println( result.get )

}

class SimpleRegex1 extends RegexParsers {
  def re = "[0-9]+".r
  def parse( input: String ) = parseAll( re, input )
}