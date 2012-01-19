package jp.mwsoft.sample.scala.gui

import java.awt.Font
import scala.collection.JavaConversions.asScalaIterator
import scala.swing.BoxPanel
import scala.swing.Button
import scala.swing.Dimension
import scala.swing.Label
import scala.swing.MainFrame
import scala.swing.Orientation
import scala.swing.SimpleSwingApplication
import javax.swing.plaf.FontUIResource
import javax.swing.UIManager
import scala.swing.event.ButtonClicked
import scala.swing.Dialog
import scala.swing.event.MouseEntered
import scala.swing.event.MouseClicked
import java.awt.event.MouseWheelEvent
import scala.swing.event.MousePressed
import scala.swing.event.MouseReleased
import scala.swing.event.KeyPressed

object ButtonEventSample extends SimpleSwingApplication {

  // Resourceの中に入れておいたフォントをデフォルトに指定する
  val baseFont = new FontUIResource( Font.createFont( Font.TRUETYPE_FONT,
    getClass().getResourceAsStream( "TakaoMincho.ttf" ) ).deriveFont( 24.0f ) )
  for ( entry <- UIManager.getDefaults().entrySet().iterator() )
    if ( entry.getKey().toString().endsWith( ".font" ) ) UIManager.put( entry.getKey(), baseFont )

  def top = new MainFrame {
    contents = new BoxPanel( Orientation.Vertical ) {
      // クリックされるとダイアログが出るボタン
      contents += new Button( "押してみろ" ) {
        reactions += {
          case e: ButtonClicked => Dialog.showMessage( message = "ボタン押された" )
        }
      }

      // イベントによってテキストが変わるラベル
      contents += new Label( "ラベル" ) {
        listenTo( mouse.clicks, mouse.moves )
        reactions += {
          case e: MousePressed  => text = "MousePressed"
          case e: MouseReleased => text = "MouseReleased"
          case e: MouseEntered  => text = "MouseEntered"
        }
      }
    }
  }

}