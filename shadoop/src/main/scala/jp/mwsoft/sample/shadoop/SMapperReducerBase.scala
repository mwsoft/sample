package jp.mwsoft.sample.shadoop

import org.apache.hadoop.io.{ Writable, IntWritable, LongWritable, FloatWritable, Text }

trait SMapperReducerBase[KEY_IN, VAL_IN, KEY_OUT, VAL_OUT] {

  def outputKeyClass: Class[KEY_OUT]
  def outputValueClass: Class[VAL_OUT]

  implicit def writable2int(value: IntWritable) = value.get
  implicit def int2writable(value: Int) = new IntWritable(value)

  implicit def writable2long(value: LongWritable) = value.get
  implicit def long2writable(value: Long) = new LongWritable(value)

  implicit def writable2float(value: FloatWritable) = value.get
  implicit def float2writable(value: Float) = new FloatWritable(value)

  implicit def text2string(value: Text) = value.toString
  implicit def string2text(value: String) = new Text(value)

  implicit def javaIterator2Iterator[A](value: java.util.Iterator[A]) = new Iterator[A] {
    def hasNext = value.hasNext
    def next = value.next
  }

  implicit def javaIterator2IntIterator(value: java.util.Iterator[IntWritable]) = new Iterator[Int] {
    def hasNext = value.hasNext
    def next = value.next.get
  }

  implicit def javaIterator2LongIterator(value: java.util.Iterator[LongWritable]) = new Iterator[Long] {
    def hasNext = value.hasNext
    def next = value.next.get
  }

  implicit def javaIterator2FloatIterator(value: java.util.Iterator[FloatWritable]) = new Iterator[Float] {
    def hasNext = value.hasNext
    def next = value.next.get
  }

  implicit def javaIterator2TextIterator(value: java.util.Iterator[Text]) = new Iterator[String] {
    def hasNext = value.hasNext
    def next = value.next.toString
  }

}