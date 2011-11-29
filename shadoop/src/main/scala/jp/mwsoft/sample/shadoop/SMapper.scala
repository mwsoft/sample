package jp.mwsoft.sample.shadoop

import org.apache.hadoop.mapreduce.{ Mapper }

abstract class SMapper[KEY_IN, VAL_IN, KEY_OUT, VAL_OUT](implicit keyOutType: Manifest[KEY_OUT], valOutType: Manifest[VAL_OUT])
    extends Mapper[KEY_IN, VAL_IN, KEY_OUT, VAL_OUT]
    with SMapperReducerBase[KEY_IN, VAL_IN, KEY_OUT, VAL_OUT] {

  def outputKeyClass = keyOutType.erasure.asInstanceOf[Class[KEY_OUT]]
  def outputValueClass = valOutType.erasure.asInstanceOf[Class[VAL_OUT]]

  type Context = Mapper[KEY_IN, VAL_IN, KEY_OUT, VAL_OUT]#Context
}
