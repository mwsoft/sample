package jp.mwsoft.sample.regexparsers

import scala.util.parsing.combinator.RegexParsers

object SimpleRegex2 extends App {

  val result = new SimpleRegex2().parse( "abc." )
  println( result.get._1 )
  println( result.get._2 )

}

class SimpleRegex2 extends RegexParsers {
  def p1 = "[a-z]+".r
  def p2 = "."
  def re = p1 ~ p2
  def parse( input: String ) = parseAll( re, input )
}