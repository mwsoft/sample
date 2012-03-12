package jp.mwsoft.sample.orm.wikipedia

import net.liftweb.mapper.DB
import net.liftweb.db.DefaultConnectionIdentifier
import org.h2.fulltext.FullTextLuceneEx

object SearchSample extends App {

  System.setProperty( "h2.luceneAnalyzer", "org.apache.lucene.analysis.ja.JapaneseAnalyzer" )
  Model.init
  search

  def search {
    val ( columns: List[String], records: List[List[String]] ) = DB.runQuery( """
SELECT W.TITLE, W.TEXT FROM FTL_SEARCH_DATA(?, 1000, 0) FT, WIKIPEDIAMODEL W
WHERE FT.TABLE='WIKIPEDIAMODEL' AND W.ID=FT.KEYS[0]""", List( "telnet" ) )

    val titleColumn = columns indexOf "TITLE"
    for ( record <- records ) {
      println( record( titleColumn ) )
    }

  }
}

