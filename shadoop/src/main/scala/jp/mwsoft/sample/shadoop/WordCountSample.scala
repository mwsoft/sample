package jp.mwsoft.sample.shadoop

import org.apache.hadoop.io.{ LongWritable, Text }
import java.text.SimpleDateFormat
import java.util.Date

object WordCountSample extends STool {

  // Mapperを設定（必須）
  override val mapper = new MyMapper()

  // Combiner（省略可）
  override val combiner = new MyCombiner()

  // Reducer（省略可）
  override val reducer = new MyReducer()

  // Toolのrunメソッドは自前で書く
  override def run( args: Array[String] ): Int = {
    // jobName, inputPath, outputPathを指定してJobを取得
    // MapperやReducerも自動的にセットされる
    val job = getJob( "wordCount", "data/in", "data/out" )

    // 足りない処理があればここらへんに書く

    // あとはrunするだけ
    run( job )
  }
}

// Mapper
class MyMapper extends SMapper[LongWritable, Text, Text, LongWritable]() {
  override def map( key: LongWritable, value: Text, context: Context ) {
    value.split( " " ).groupBy( x => x ).map( x => ( x._1, x._2.size ) ) foreach ( x => context.write( x._1, x._2 ) )
  }
}

// Combiner
class MyCombiner extends SReducer[Text, LongWritable, Text, LongWritable]() {
  override def reduce( key: Text, values: java.lang.Iterable[LongWritable], context: Context ) {
    val sum = values.iterator().sum
    context.write( key, sum.toLong )
  }
}

// Reducer
class MyReducer extends SReducer[Text, LongWritable, Text, LongWritable]() {
  override def reduce( key: Text, values: java.lang.Iterable[LongWritable], context: Context ) {
    val sum = values.iterator().sum
    if ( sum >= 3 ) context.write( key, sum.toLong )
  }
}
