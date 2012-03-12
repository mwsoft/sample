package jp.mwsoft.sample.orm.wikipedia

import net.liftweb.mapper._
import java.util.Date
import org.h2.fulltext.FullTextLuceneEx

object Model {
  import net.liftweb.common.Empty
  val vendor = new StandardDBVendor( "org.h2.Driver", "jdbc:h2:db", Empty, Empty )

  def init {
    DB.defineConnectionManager( DefaultConnectionIdentifier, vendor )
    Schemifier.schemify( true, Schemifier.infoF _, WikipediaModel )
  }

  def luceneInit {
    System.setProperty( "h2.luceneAnalyzer", "org.apache.lucene.analysis.ja.JapaneseAnalyzer" )
    System.setProperty( "h2.isTriggerCommit", "false" )
    Model.init
    DB.use( DefaultConnectionIdentifier ) { conn =>
      conn.setAutoCommit( false )
      FullTextLuceneEx.init( conn )
      //FullTextLuceneEx.createIndex( conn, "PUBLIC", "WIKIPEDIAMODEL", "TEXT" )
    }
  }
}

class WikipediaModel extends LongKeyedMapper[WikipediaModel] with IdPK {
  def getSingleton = WikipediaModel
  object articleId extends MappedString( this, 64 )
  object title extends MappedString( this, 256 )
  object titleAnnotation extends MappedString( this, 256 )
  object text extends MappedText( this )
  object textCount extends MappedInt( this )
  object lastModified extends MappedDateTime( this )

  def check = ( !articleId.isEmpty() ) && ( !title.isEmpty() ) && ( !text.isEmpty() )
}

object WikipediaModel extends WikipediaModel with LongKeyedMetaMapper[WikipediaModel] {
  override def fieldOrder = List( id, title, titleAnnotation, text, textCount, lastModified )
}