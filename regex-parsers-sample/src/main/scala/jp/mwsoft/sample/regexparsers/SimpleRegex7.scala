package jp.mwsoft.sample.regexparsers

import scala.util.parsing.combinator.RegexParsers

object SimpleRegex7 extends App {

  val result = new SimpleRegex7().parse( """abc123def456ghi789""" )
  println( result )

}

class SimpleRegex7 extends RegexParsers {
  def alpha = "[a-z]".r
  def num = "[0-9]".r
  def re = rep( alpha.* ~> ( num.+  <~ alpha.* ) )
  def parse( input: String ) = parseAll( re, input ) match {
    case Success( result, _ ) => result.flatMap(x => x).mkString
  }
}
