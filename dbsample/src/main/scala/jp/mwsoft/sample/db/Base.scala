package jp.mwsoft.sample.db

import java.sql.Connection

trait Base {

  def getConnection: Connection

  def doClose[T](f: Connection => T) {
    val conn = getConnection
    try f(conn)
    finally conn.close
  }

  def select = doClose { conn =>
    val stmt = conn.createStatement
    val rset = stmt.executeQuery("select * from sample order by id")
    while (rset.next) {
      println(rset.getString(1), rset.getString(2))
    }
  }

  def delete = doClose { conn =>
    val stmt = conn.createStatement
    stmt.execute("delete from sample")
  }

  def insert(id: Int) = doClose { conn =>
    val stmt = conn.createStatement
    for (i <- 0 to 10000) {
      stmt.execute("insert into sample (value) values ( '%d-%d' )" format (id, i))
    }
  }

}