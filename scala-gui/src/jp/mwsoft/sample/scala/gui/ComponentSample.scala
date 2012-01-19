package jp.mwsoft.sample.scala.gui
import scala.swing.SimpleSwingApplication
import scala.swing.MainFrame
import javax.swing.UIManager
import javax.swing.plaf.FontUIResource
import java.awt.Font
import scala.collection.JavaConversions.asScalaIterator
import scala.swing.TextField
import scala.swing.BoxPanel
import scala.swing.Orientation
import java.awt.Dimension
import scala.swing.TextArea
import scala.swing.CheckBox
import scala.swing.ButtonGroup
import scala.swing.RadioButton
import scala.swing.PasswordField
import scala.swing.ProgressBar
import scala.swing.Slider

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