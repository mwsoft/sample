package jp.mwsoft.sample.squeryl

import org.squeryl.{ Schema, SessionFactory, Session, KeyedEntity }
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.annotations.Column
import org.squeryl.PrimitiveTypeMode._
import java.sql.{ DriverManager }

object SquerylSample3 extends App {

  // Sessionの設定
  Class.forName( "com.mysql.jdbc.Driver" )
  SessionFactory.concreteFactory = Some( () => Session.create(
    DriverManager.getConnection( "jdbc:mysql://localhost:3306/squeryl_test", "root", "" ),
    new MySQLAdapter ) )

  transaction {
    // 登録
    val message = DB.message.insert( new Message( "mw", "hello, world" ) )
    // 削除
    DB.message.deleteWhere( m => m.id === 1 )
    DB.message.deleteWhere( m => m.id === 2 and m.user === "mw" )
    // 更新
    DB.message.update( new Message( "mw", "hello squeryl", 3L ) )
    update( DB.message )( m => where( m.id === 4L ) set ( m.text := "update squeryl" ) )
  }

  /** ユーザ名とテキストを保持するメッセージテーブル */
  class Message(
    val user: String,
    var text: String,
    val id: Long = 0L ) extends KeyedEntity[Long]

  object DB extends Schema {
    val message = table[Message]
  }
}

