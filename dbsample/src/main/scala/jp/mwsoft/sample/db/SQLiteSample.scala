package jp.mwsoft.sample.db

import java.sql.Connection
import java.sql.DriverManager

import scala.concurrent.ops.par

object SQLiteSample extends Base {

  override def getConnection: Connection = {
    Class.forName("org.sqlite.JDBC")
    DriverManager.getConnection("jdbc:sqlite:sample.sqlite.db")
  }

  def parTest {
    //create
    par(insert(1), insert(2))
    select
    delete
  }

  def create = doClose { conn =>
    val stmt = conn.createStatement
    //stmt.execute("drop table sample")
    stmt.execute("create table sample (id identity, value varchar(255))")
  }

}