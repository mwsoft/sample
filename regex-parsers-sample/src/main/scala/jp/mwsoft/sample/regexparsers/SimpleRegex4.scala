package jp.mwsoft.sample.regexparsers

import scala.util.parsing.combinator.RegexParsers

object SimpleRegex4 extends App {

  val result = new SimpleRegex4().parse( """abc.""" )
  println( result.get )

}

class SimpleRegex4 extends RegexParsers {
  def p1 = "[a-z]+".r
  def p2 = """[\.]""".r
  def re = p1 <~ p2 ^^ ( _.toUpperCase() )
  def parse( input: String ) = parseAll( re, input )
}
