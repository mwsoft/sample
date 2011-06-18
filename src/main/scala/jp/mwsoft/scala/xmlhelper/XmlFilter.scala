package jp.mwsoft.scala.xmlhelper

import scala.xml.NodeSeq

object XmlFilter {
  implicit def nodeSeqToMwSoftXmlFilter(nodeSeq: NodeSeq): XmlFilter = new XmlFilter(nodeSeq)
}

class XmlFilter(that: NodeSeq) {

  import XmlFilter._

  /** 属性用のFilter */
  def attrFilter(name: String, f: String => Boolean): NodeSeq = {
    that filter (_ \ ("@" + name) exists (e => f(e.text)))
  }

  /** 属性用のFilter（完全一致） */
  def attrFilter(name: String, value: String): NodeSeq = {
    attrFilter(name, (arg: String) => { arg == value })
  }

  /** 属性用のFilter（正規表現） */
  def attrReFilter(name: String, regex: String): NodeSeq = {
    attrFilter(name, (arg: String) => { arg matches regex })
  }

  /** class属性用のFilter */
  def classFilter(className: String) = {
    that filter (_ \ ("@class") exists (_.text.split(" ") contains className))
  }

  /** attrFilter付き\\ */
  def \\@(nodeName: String, attrName: String, value: String, f: (String) => Boolean): NodeSeq = {
    that \\ nodeName attrFilter (attrName, f)
  }

  /** attrFilter付き\\ */
  def \\@(nodeName: String, attrName: String, value: String): NodeSeq = {
    \\@(nodeName, attrName, value, (arg: String) => { arg == value })
  }

  /** attrFilter付き\\ */
  def \@(nodeName: String, attrName: String, value: String, f: (String) => Boolean): NodeSeq = {
    that \ nodeName attrFilter (attrName, f)
  }

  /** attrFilter付き\ */
  def \@(nodeName: String, attrName: String, value: String): NodeSeq = {
    \@(nodeName, attrName, value, (arg: String) => { arg == value })
  }
}
