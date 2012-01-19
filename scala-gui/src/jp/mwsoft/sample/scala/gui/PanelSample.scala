package jp.mwsoft.sample.scala.gui

import scala.swing.{ SimpleSwingApplication, MainFrame, Dimension, Label }
import scala.swing.BoxPanel
import scala.swing.Orientation
import java.awt.Font
import java.io.File
import javax.swing.UIManager
import javax.swing.plaf.FontUIResource
import scala.collection.JavaConversions.asScalaIterator
import scala.swing.Button
import scala.swing.BorderPanel
import scala.swing.FlowPanel
import scala.swing.GridPanel
import scala.swing.GridBagPanel

object PanelSample extends SimpleSwingApplication {

  // Resourceの中に入れておいたフォントをデフォルトに指定する
  val baseFont = new FontUIResource( Font.createFont( Font.TRUETYPE_FONT,
    getClass().getResourceAsStream( "TakaoMincho.ttf" ) ).deriveFont( 14.0f ) )
  for ( entry <- UIManager.getDefaults().entrySet().iterator() )
    if ( entry.getKey().toString().endsWith( ".font" ) ) UIManager.put( entry.getKey(), baseFont )

  def top = new MainFrame {
    title = "Window Title"
    minimumSize = new Dimension( 300, 100 )
    // コンテンツにPanelを設定
    //contents = boxPanel
    //contents = borderPanel
    //contents = flowPanel
    //contents = gridPanel
    contents = gridBagPanel
  }

  // Vertial（縦）かHorizontal（横）に並べてコンポーネントを配置
  val boxPanel = new BoxPanel( Orientation.Vertical ) {
    contents += new Label( "展覧会の絵" )
    contents += new Label( "クープランの墓" )
    contents += new Button( "亡き王女のためのパヴァーヌ" )
  }

  // North, South, East, West, Centerのいずれかにコンポーネントを配置
  val borderPanel = new BorderPanel() {
    add( new Label( "展覧会の絵" ), BorderPanel.Position.North )
    add( new Label( "クープランの墓" ), BorderPanel.Position.East )
    add( new Button( "亡き王女のためのパヴァーヌ" ), BorderPanel.Position.South )
  }

  // Horizontal（横）に並べてコンポーネントを配置
  val flowPanel = new FlowPanel() {
    contents += new Label( "展覧会の絵" )
    contents += new Label( "クープランの墓" )
    contents += new Button( "亡き王女のためのパヴァーヌ" )
  }

  // HTMLのテーブルみたいに（下記の例だと縦3、横２マス）配置
  val gridPanel = new GridPanel( 3, 2 ) {
    contents += new Label( "展覧会" )
    contents += new Label( "の絵" )
    contents += new Label( "クープラン" )
    contents += new Label( "の墓" )
    contents += new Button( "亡き王女の" )
    contents += new Button( "ためのパヴァーヌ" )
  }

  // HTMLのテーブルで言うところのcolspanやrowspan的なことができる
  val gridBagPanel = new GridBagPanel() {
    layout += new Label( "展覧会" ) -> ( 0, 0 )
    layout += new Label( "の絵" ) -> ( 1, 0 )
    layout += new Label( "クープラン" ) -> ( 0, 1 )
    layout += new Label( "の墓" ) -> ( 1, 1 )

    val c = pair2Constraints( 0, 2 )
    c.gridwidth = 2
    layout += new Button( "亡き王女のためのパヴァーヌ" ) -> c
  }
}

