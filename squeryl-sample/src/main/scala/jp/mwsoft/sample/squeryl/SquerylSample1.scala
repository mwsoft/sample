package jp.mwsoft.sample.squeryl

import org.squeryl.{ Schema, SessionFactory, Session, KeyedEntity }
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.annotations.Column
import org.squeryl.PrimitiveTypeMode.transaction
import java.sql.{ DriverManager }

object SquerylSample1 extends App {

  // Sessionの設定
  Class.forName( "com.mysql.jdbc.Driver" )
  SessionFactory.concreteFactory = Some( () => Session.create(
    DriverManager.getConnection( "jdbc:mysql://localhost:3306/squeryl_test", "root", "" ),
    new MySQLAdapter ) )

  // CREATE/DROPの実行
  transaction {
    // テーブルの削除
    DB.drop
    // テーブルの作成
    DB.create
    // DDLの表示
    DB.printDdl
    // 個別のテーブル操作
    DB.message.schema.drop
    DB.message.schema.create
  }

  /** ユーザ名とテキストを保持するメッセージテーブル */
  class Message(
    val user: String,
    val text: String,
    val id: Long = 0L ) extends KeyedEntity[Long]

  /** サンプル用のテーブルをもう1個用意しとく */
  class Message2(
    val user: String,
    @Column( "message_text" ) val text: String,
    val id: Long = 0L ) extends KeyedEntity[Long]

  object DB extends Schema {
    val message = table[Message]
    val message2 = table[Message2]( "message_two" )
  }
}

