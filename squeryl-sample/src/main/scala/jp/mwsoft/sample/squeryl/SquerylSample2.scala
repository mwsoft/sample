package jp.mwsoft.sample.squeryl

import org.squeryl.{ Schema, SessionFactory, Session, KeyedEntity }
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.annotations.Column
import org.squeryl.PrimitiveTypeMode._
import java.sql.{ DriverManager }

object SquerylSample2 extends App {

  // Sessionの設定
  Class.forName( "com.mysql.jdbc.Driver" )
  SessionFactory.concreteFactory = Some( () => Session.create(
    DriverManager.getConnection( "jdbc:mysql://localhost:3306/squeryl_test", "root", "" ),
    new MySQLAdapter ) )

  // CREATE/DROPの実行
  transaction {
    DB.printDdl
    DB.drop
    DB.create
  }

  /** ユーザ名とテキストを保持するメッセージテーブル */
  class Message(
    val user: String,
    val text: Option[String] = None,
    val id: Long = 0L ) extends KeyedEntity[Long]

  object DB extends Schema {
    val message = table[Message]
    on( message )( m => declare(
      // uniq指定してみる
      m.id is ( autoIncremented ),
      // indexを貼って、varchar(64)にしてみる
      m.user is ( indexed, dbType( "varchar(64)" ) ),
      m.user defaultsTo ( "uhakemo" ),
      // varchar(1024)にしてみる
      m.text is ( dbType( "varchar(1024)" ) )
    ) )
  }
}

