package jp.mwsoft.sample.regexparsers

import scala.util.parsing.combinator.RegexParsers

object SimpleRegex5 extends App {

  val result = new SimpleRegex5().parse( """abc,""" )
  println( result )

}

class SimpleRegex5 extends RegexParsers {
  def re = "てきとー".r
  def parse( input: String ) = parseAll( re, input ) match {
    case Success( result, _ ) => Option( result )
    case _                    => None
  }
}
