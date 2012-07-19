package jp.mwsoft.sample.scalatest

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSpec

@RunWith( classOf[JUnitRunner] )
class SampleClassTest extends FunSpec with ShouldMatchers {
  it( "1+2は3だよ" ) {
    SampleClass.add( 1, 2 ) should equal( 3 )
  }
}