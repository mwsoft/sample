package jp.mwsoft.sample.scala.gui

import scala.swing.{ SimpleSwingApplication, MainFrame, Dimension, Label }
import java.awt.{ Cursor, Color, Image }
import javax.imageio.ImageIO
import java.io.FileInputStream
import scala.swing.MenuBar
import scala.swing.Menu

object MainFrameSample extends SimpleSwingApplication {
  def top = new MainFrame {
    // Windowのタイトル
    title = "Window Title"
    // Windowのサイズ
    minimumSize = new Dimension( 300, 200 )
    // カーソルの設定
    cursor = new Cursor( Cursor.HAND_CURSOR )
    // リサイズ不能
    resizable = false
    // アイコンの設定
    iconImage = ImageIO.read( getClass().getResourceAsStream( "icon.bmp" ) )
    // メニューバーの指定
    menuBar = new MenuBar() {
      contents += new Menu( "menu1" )
    }
  }
}