package jp.mwsoft.sample.scala.gui
import java.awt.Font

import scala.collection.JavaConversions.asScalaIterator
import scala.swing.BoxPanel
import scala.swing.ButtonGroup
import scala.swing.CheckBox
import scala.swing.MainFrame
import scala.swing.Orientation
import scala.swing.PasswordField
import scala.swing.ProgressBar
import scala.swing.RadioButton
import scala.swing.SimpleSwingApplication
import scala.swing.Slider
import scala.swing.TextArea
import scala.swing.TextField

import javax.swing.plaf.FontUIResource
import javax.swing.UIManager

object ComponentSample extends SimpleSwingApplication {

  // Resourceの中に入れておいたフォントをデフォルトに指定する
  val baseFont = new FontUIResource( Font.createFont( Font.TRUETYPE_FONT,
    getClass().getResourceAsStream( "TakaoMincho.ttf" ) ).deriveFont( 18.0f ) )
  for ( entry <- UIManager.getDefaults().entrySet().iterator() )
    if ( entry.getKey().toString().endsWith( ".font" ) ) UIManager.put( entry.getKey(), baseFont )

  def top = new MainFrame {
    contents = new BoxPanel( Orientation.Vertical ) {
      // テキストフィールド
      contents += new TextField( "テキスト" )
      // パスワード
      contents += new PasswordField( "password" )
      // テキストエリア
      contents += new TextArea( "テキストエリア" )
      // チェックボックス
      contents += new CheckBox( "チェックボックス" )
      // ラジオボタン
      val button1 = new RadioButton( "ラジオボタン１" )
      val button2 = new RadioButton( "ラジオボタン２" )
      val group = new ButtonGroup( button1, button2 )
      contents += button1
      contents += button2
      // プログレスバー
      val progressBar = new ProgressBar()
      progressBar.value = 30
      contents += progressBar
      // スライダー
      contents += new Slider()
    }
  }

}