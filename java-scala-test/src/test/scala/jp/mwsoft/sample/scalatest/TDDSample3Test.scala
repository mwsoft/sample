package jp.mwsoft.sample.scalatest

import java.util.Arrays
import scala.collection.JavaConversions.asScalaBuffer
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith( classOf[JUnitRunner] )
class TDDSample3Test extends FunSuite with ShouldMatchers {

  val sample3 = new TDDSample3()
  sample3.setStr( "文字列" )
  sample3.setI( 30 );

  test( "sample3は「文字列」と「30」を格納してるよ" ) {
    sample3 should have(
      'str( "文字列" ),
      'i( 30 )
    )
  }
}