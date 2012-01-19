package jp.mwsoft.sample.scala.gui

import java.awt.Color
import java.awt.Font

import scala.collection.JavaConversions.asScalaIterator
import scala.swing.Dimension
import scala.swing.BoxPanel
import scala.swing.Button
import scala.swing.Label
import scala.swing.MainFrame
import scala.swing.Orientation
import scala.swing.SimpleSwingApplication

import javax.swing.border.LineBorder
import javax.swing.plaf.FontUIResource
import javax.swing.UIManager

object BoxPanelSample extends SimpleSwingApplication {

  // Resourceの中に入れておいたフォントをデフォルトに指定する
  val baseFont = new FontUIResource( Font.createFont( Font.TRUETYPE_FONT,
    getClass().getResourceAsStream( "TakaoMincho.ttf" ) ).deriveFont( 14.0f ) )
  for ( entry <- UIManager.getDefaults().entrySet().iterator() )
    if ( entry.getKey().toString().endsWith( ".font" ) ) UIManager.put( entry.getKey(), baseFont )

  def top = new MainFrame {
    title = "Window Title"
    minimumSize = new Dimension( 300, 100 )
    //iconImage = ImageIO.read( getClass().getResourceAsStream( "icon.bmp" ) )

    // コンテンツにPanelを設定
    contents = new BoxPanel( Orientation.Vertical ) {
      // コンポーネントの配置
      contents += new Label( "展覧会の絵" )
      contents += new Label( "クープランの墓" )
      contents += new Button( "亡き王女のためのパヴァーヌ" )
      // 背景色
      background = Color.CYAN
      // ツールチップ
      tooltip = "ツールチップ"
      // ボーダー
      border = new LineBorder( Color.WHITE, 3 )
    }
  }

}