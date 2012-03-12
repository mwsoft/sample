package jp.mwsoft.sample.orm.wikipedia

import java.text.SimpleDateFormat

import javax.xml.stream.XMLStreamConstants
import javax.xml.stream.XMLEventReader
import javax.xml.stream.events.XMLEvent

object Parser {

  val sdf = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss'Z'" )

  /** page element内の解析 */
  def pageParse( reader: XMLEventReader ): WikipediaModel = {
    val model = new WikipediaModel()

    def loop: Unit = if ( reader.hasNext() ) {
      val event = reader.nextEvent()

      // revision elementの解析は、revisonParseにて行う
      if ( isStartElem( event, "revision" ) )
        revisionParse( reader, model );
      // title
      else if ( isStartElem( event, "title" ) ) {
        val title = getText( reader, "title" );
        // タイトルにコロンが含まれる場合は管理用記事なのでスキップする
        if ( title.indexOf( ':' ) != -1 ) return
        // (曖昧さ回避)や(音楽)などの注釈文字を外す
        val posStart = title.indexOf( " (" );
        val posEnd = title.indexOf( ')', posStart );
        if ( posStart != -1 && posEnd != -1 ) {
          model.title( title.substring( 0, posStart ) )
          model.titleAnnotation( title.substring( posStart + 2, posEnd ) )
        }
        else {
          model.title( title )
        }
      }
      else if ( isStartElem( event, "id" ) ) {
        model.articleId( getText( reader, "id" ) )
      }

      if ( !isEndElem( event, "page" ) ) loop
    }; loop

    model
  }

  /** revision element内の解析 */
  def revisionParse( reader: XMLEventReader, model: WikipediaModel ) {
    def loop: Unit = if ( reader.hasNext() ) {
      val event = reader.nextEvent()
      if ( isStartElem( event, "text" ) )
        model.text( getText( reader, "text" ) )
      else if ( isStartElem( event, "timestamp" ) )
        model.lastModified( sdf.parse( getText( reader, "timestamp" ) ) )
      if ( !isEndElem( event, "revision" ) ) loop
    }; loop
  }

  /** 指定のend tagを発見するまで、CHARACTERSを取得 */
  def getText( reader: XMLEventReader, name: String ): String = {
    val builder = new StringBuilder();
    def loop: Unit = if ( reader.hasNext() ) {
      val event = reader.nextEvent();
      if ( event.getEventType() == XMLStreamConstants.CHARACTERS ) {
        val data = event.asCharacters().getData().trim();
        if ( data.length() > 0 )
          builder.append( data );
      }
      if ( !isEndElem( event, name ) ) loop
    }; loop

    return builder.toString();
  }

  /** 指定名のStart Elementか判定する */
  def isStartElem( event: XMLEvent, name: String ): Boolean =
    event.getEventType() == XMLStreamConstants.START_ELEMENT &&
      name.equals( event.asStartElement().getName().getLocalPart() );

  /** 指定名のEnd Elementか判定する */
  def isEndElem( event: XMLEvent, name: String ): Boolean =
    event.getEventType() == XMLStreamConstants.END_ELEMENT &&
      name.equals( event.asEndElement().getName().getLocalPart() )
}