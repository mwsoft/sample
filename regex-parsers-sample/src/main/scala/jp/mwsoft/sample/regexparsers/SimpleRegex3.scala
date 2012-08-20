package jp.mwsoft.sample.regexparsers

import scala.util.parsing.combinator.RegexParsers

object SimpleRegex3 extends App {

  val result = new SimpleRegex3().parse( "abc." )
  println( result.get )

}

class SimpleRegex3 extends RegexParsers {
  def p1 = "[a-z]+".r
  def p2 = "."
  def re = p1 <~ p2
  def parse( input: String ) = parseAll( re, input )
}