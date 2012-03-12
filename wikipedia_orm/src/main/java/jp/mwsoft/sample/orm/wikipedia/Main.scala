package jp.mwsoft.sample.orm.wikipedia

import java.io.BufferedInputStream
import java.io.FileInputStream
import java.sql.DriverManager
import java.util.Date
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream
import javax.xml.stream.XMLInputFactory
import net.liftweb.mapper.DB
import net.liftweb.db.DefaultConnectionIdentifier

object Main extends App {

  val factory = XMLInputFactory.newInstance()
  val reader = factory.createXMLEventReader( ( new BZip2CompressorInputStream(
    new BufferedInputStream( new FileInputStream( "jawiki-latest-pages-articles.xml.bz2" ) ) ) ) )

  Model.init

  val start = System.currentTimeMillis()
  try {
    var counter = 0
    DB.use( DefaultConnectionIdentifier ) { conn =>
      conn.setAutoCommit( false )
      while ( reader.hasNext() ) {
        if ( { counter += 1; counter } % 1000 == 0 ) println( counter )
        val model = Parser.pageParse( reader )
        if ( model.check ) model.save
      }
    }
  }
  finally reader.close()
  println( System.currentTimeMillis() - start )

  Model.luceneInit

  println( System.currentTimeMillis() - start )
  //1,091,126
}

