package jp.mwsoft.sample.db

import java.sql.Connection
import java.sql.DriverManager

import scala.concurrent.ops.par

object H2Sample extends Base {

  override def getConnection: Connection = {
    // Class.forName("org.h2.Driver")
    DriverManager.getConnection("jdbc:h2:sample", "sa", "")
  }

  def parTest {
    create
    par(insert(1), insert(2))
    select
    delete
  }

  def csvRead = doClose { conn =>
    val stmt = conn.createStatement
    //val rset = stmt.executeQuery("select id, param from csvread('file_name.csv')")
    val rset = stmt.executeQuery("select id, param from csvread('file_name.csv', null, 'Shift_JIS', chr(9))")
    while (rset.next) {
      println(rset.getString(1), rset.getString(2))
    }
  }

  def csvWrite = doClose { conn =>
    val stmt = conn.createStatement
    // stmt.execute("call csvwrite('test2.csv', 'select param from csvread(''file_name.csv'')')")
    stmt.execute("call csvwrite('test2.csv', 'select * from csvread(''file_name.csv'')', 'Shift_JIS', chr(9))")
  }

  def create = doClose { conn =>
    val stmt = conn.createStatement
    stmt.execute("drop table sample")
    stmt.execute("create table sample (id identity, value varchar(255))")
  }

}