package jp.mwsoft.sample.squeryl

import org.squeryl.{ Schema, SessionFactory, Session, KeyedEntity }
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.annotations.Column
import org.squeryl.PrimitiveTypeMode._
import java.sql.{ DriverManager }

object SquerylSample5 extends App {

  // Sessionの設定
  Class.forName( "com.mysql.jdbc.Driver" )
  SessionFactory.concreteFactory = Some( () => Session.create(
    DriverManager.getConnection( "jdbc:mysql://localhost:3306/squeryl_test", "root", "" ),
    new MySQLAdapter ) )

  transaction {

    // テストデータを登録
    // DB.create
    // DB.userName.insert( UserName( "foo" ) )
    // DB.userEmail.insert( UserEmail( "foo@bar.baz" ) )
    // DB.userName.insert( UserName( "bar" ) )
    // DB.userEmail.insert( UserEmail( "bar@bar.baz" ) )
    // DB.userName.insert( UserName( "baz" ) )

    // whereで結合
    val users1 = from( DB.userName, DB.userEmail )( ( name, mail ) =>
      where( name.id === mail.id ) select ( name.id, name.name, mail.email ) )
    users1 foreach println

    // joinとonで結合
    val users2 = join( DB.userName, DB.userEmail )( ( name, mail ) =>
      select( name.id, name.name, mail.email ) on ( name.id === mail.id ) )
    users2 foreach println

    // joinでleftOuterを指定して外部結合
    val users3 = join( DB.userName, DB.userEmail.leftOuter )( ( name, mail ) =>
      select( name.id, name.name, mail.map( _.email ) ) on ( name.id === mail.map( _.id ) ) )
    users3 foreach println

    // select内にleftOuterJoinを書いて外部結合
    val user4 = from( DB.userName, DB.userEmail )( (name, mail) =>
      select (name.name, leftOuterJoin(mail, name.id === mail.id)) )
    user4 foreach println
  }

  /** ユーザ名を持つテーブル */
  case class UserName(
    val name: String,
    val id: Long = 0L ) extends KeyedEntity[Long]

  case class UserEmail(
    val email: String,
    val id: Long = 0L ) extends KeyedEntity[Long]

  object DB extends Schema {
    val userName = table[UserName]
    val userEmail = table[UserEmail]
  }
}

