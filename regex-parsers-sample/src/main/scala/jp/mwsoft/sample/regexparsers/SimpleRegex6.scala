package jp.mwsoft.sample.regexparsers

import scala.util.parsing.combinator.RegexParsers

object SimpleRegex6 extends App {

  val result = new SimpleRegex6().parse( """abc123def456ghi789""" )
  println( result.get )

}

class SimpleRegex6 extends RegexParsers {
  def alpha = "[a-z]".r
  def num = "[0-9]".r
  def re = rep( alpha.* ~> num.+ <~ alpha.* )
  def parse( input: String ) = parseAll( re, input )
}
