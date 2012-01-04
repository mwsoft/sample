package jp.mwsoft.sample.squeryl

import org.squeryl.{ Schema, SessionFactory, Session, KeyedEntity }
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.annotations.Column
import org.squeryl.PrimitiveTypeMode._
import java.sql.{ DriverManager }

object SquerylSample4 extends App {

  // Sessionの設定
  Class.forName( "com.mysql.jdbc.Driver" )
  SessionFactory.concreteFactory = Some( () => Session.create(
    DriverManager.getConnection( "jdbc:mysql://localhost:3306/squeryl_test", "root", "" ),
    new MySQLAdapter ) )

  transaction {
    // from, where, selectで取得
    val messages1 = from( DB.message )( m => where( m.user === "user_name" ) select ( m ) )
    messages1 foreach println

    // seqで取得
    val messages2 = DB.message.seq
    messages2 foreach println

    // キーを指定してlookupで1行取得
    val message = DB.message.lookup( 3L )
    println( message )

    // distinct
    val messages3 = from( DB.message )( m => select( m.text ) ).distinct
    messages3 foreach println

    // order by
    val messages4 = from( DB.message )( m => select( m ) orderBy ( m.id asc ) )
    messages4 foreach ( m => println( m.id ) )

    // group by
    val messages5 = from( DB.message )( m => groupBy( m.user ) compute ( m.user, count( m.id ) ) )
    messages5 foreach ( m => println( m.measures ) )
  }

  /** ユーザ名とテキストを保持するメッセージテーブル */
  case class Message(
      val user: String,
      var text: String,
      val id: Long = 0L ) extends KeyedEntity[Long] {

  }

  object DB extends Schema {
    val message = table[Message]
  }
}

