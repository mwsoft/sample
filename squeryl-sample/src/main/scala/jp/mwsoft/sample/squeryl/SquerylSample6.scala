package jp.mwsoft.sample.squeryl

import org.squeryl.{ Schema, SessionFactory, Session, KeyedEntity, Query }
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.annotations.Column
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.dsl.ast.FunctionNode
import org.squeryl.dsl.{ StringExpression, DateExpression, NumericalExpression }
import java.sql.{ DriverManager }
import java.util.Date

object SquerylSample6 extends App {

  // Sessionの設定
  Class.forName( "com.mysql.jdbc.Driver" )
  SessionFactory.concreteFactory = Some( () => Session.create(
    DriverManager.getConnection( "jdbc:mysql://localhost:3306/squeryl_test", "root", "" ),
    new MySQLAdapter ) )

  transaction {

    // テストデータの投入
    // DB.create
    // DB.user.insert( User( "0sai", new java.util.Date() ) )

    // year関数を使ってみる
    class Year( e: DateExpression[Date] ) extends FunctionNode[Int]( "year", e ) with NumericalExpression[Int]
    val users1 = from( DB.user )( name => where( new Year( name.birthday ) === 2012 ) select ( name ) )
    users1 foreach println
    println( users1.statement )

    // char_length関数を使ってみる
    class CharLength( e: StringExpression[String] ) extends FunctionNode[Int]( "char_length", e ) with NumericalExpression[Int]
    val users2 = from( DB.user )( name => where( new CharLength( name.name ) === 4 ) select ( name ) )
    users2 foreach println
    println( users2.statement )
  }

  /** ユーザ名を持つテーブル */
  case class User(
    val name: String,
    val birthday: Date,
    val id: Long = 0L ) extends KeyedEntity[Long]

  object DB extends Schema {
    val user = table[User]
  }
}

