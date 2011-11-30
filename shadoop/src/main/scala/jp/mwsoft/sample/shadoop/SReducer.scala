package jp.mwsoft.sample.shadoop

import org.apache.hadoop.mapreduce.{ Reducer }

abstract class SReducer[KEY_IN, VAL_IN, KEY_OUT, VAL_OUT](implicit keyOutType: Manifest[KEY_OUT], valOutType: Manifest[VAL_OUT])
    extends Reducer[KEY_IN, VAL_IN, KEY_OUT, VAL_OUT]
    with SMapperReducerBase[KEY_IN, VAL_IN, KEY_OUT, VAL_OUT] {

  type Context = Reducer[KEY_IN, VAL_IN, KEY_OUT, VAL_OUT]#Context

  def reduce(key: KEY_IN, values: Iterator[VAL_IN], context: Context)

  override def reduce(key: KEY_IN, values: java.lang.Iterable[VAL_IN], context: Context) {
    reduce(key, javaIterator2Iterator(values.iterator()), context)
  }

  def outputKeyClass = keyOutType.erasure.asInstanceOf[Class[KEY_OUT]]
  def outputValueClass = valOutType.erasure.asInstanceOf[Class[VAL_OUT]]
}