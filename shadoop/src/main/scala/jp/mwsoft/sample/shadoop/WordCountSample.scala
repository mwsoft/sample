package jp.mwsoft.sample.shadoop

import org.apache.hadoop.io.{ LongWritable, Text }
import java.text.SimpleDateFormat
import java.util.Date

object WordCountSample extends STool {

  // Toolのrunメソッドは自前で書く
  override def run(args: Array[String]): Int = {
    // jobName, inputPath, outputPathを指定してJobを取得
    // MapperやReducerも自動的にセットされる
    val job = createJob("wordCount", "data/in", "data/out")

    // 足りない処理があればここらへんに書く
    // job.setGroupingComparatorClass(...) みたいな

    // あとはrunするだけ
    run(job)
  }

  // Mapperを設定（必須）
  override val mapper = new SMapper[LongWritable, Text, Text, LongWritable]() {
    override def map(key: LongWritable, value: Text, context: Context) {
      value.split(" ").groupBy(x => x).map(x => (x._1, x._2.size)) foreach (x => context.write(x._1, x._2))
    }
  }

  // Combiner（省略可）
  override val combiner = new SReducer[Text, LongWritable, Text, LongWritable]() {
    override def reduce(key: Text, values: Iterator[LongWritable], context: Context) {
      val sum = values reduceLeft (_ + _)
      context.write(key, sum.toLong)
    }
  }

  // Reducer（省略可）
  override val reducer = new SReducer[Text, LongWritable, Text, LongWritable]() {
    override def reduce(key: Text, values: Iterator[LongWritable], context: Context) {
      val sum = values reduceLeft (_ + _)
      if (sum >= 3) context.write(key, sum.toLong)
    }
  }

}
