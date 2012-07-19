package jp.mwsoft.sample.scalatest

import java.util.Arrays
import scala.collection.JavaConversions.asScalaBuffer
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith( classOf[JUnitRunner] )
class TDDSample2Test extends FunSuite with ShouldMatchers {

  val list = Arrays.asList( 10, 5, 4, 4 )

  test( "listの0番目の要素は10だよ" ) {
    list( 0 ) should be( 10 )
  }

  test( "listの1番目の要素は、4〜8（6-2〜6+2）の間の数字だよ" ) {
    list( 1 ) should be( 6 plusOrMinus 2 )
  }

  test( "listの長さは4だよ" ) {
    list should have length ( 4 )
  }

  test( "listは要素の中に5か6がいるよ" ) {
    list should ( contain( 5 ) or contain( 6 ) )
  }

}