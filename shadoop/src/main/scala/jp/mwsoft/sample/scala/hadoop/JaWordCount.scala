package jp.mwsoft.sample.scala.hadoop

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{ LongWritable, Text }
import org.apache.hadoop.mapreduce.{ Job, Mapper, Reducer }
import org.apache.hadoop.mapreduce.lib.input.{ FileInputFormat, TextInputFormat }
import org.apache.hadoop.mapreduce.lib.output.{ FileOutputFormat, TextOutputFormat }
import org.atilika.kuromoji.Tokenizer
import scala.collection.JavaConversions.asScalaIterator
import java.text.Normalizer

object JaWordCount extends App {

  val conf = new Configuration( true )
  val job = new Job( conf, "wordcount" )

  // 入出力ファイルの設定
  FileInputFormat.setInputPaths( job, new Path( "data/in" ) )
  FileOutputFormat.setOutputPath( job, new Path( "data/out" ) )

  job.setJarByClass( classOf[WordCountMapper] )

  // Mapperの設定
  job.setMapperClass( classOf[WordCountMapper] )
  job.setMapOutputKeyClass( classOf[Text] )
  job.setMapOutputValueClass( classOf[LongWritable] )

  // Reducerの設定
  job.setReducerClass( classOf[WordCountReducer] )
  job.setOutputKeyClass( classOf[Text] )
  job.setOutputValueClass( classOf[LongWritable] );

  // Combinerの設定
  job.setCombinerClass( classOf[WordCountReducer] )

  // 実行
  job.waitForCompletion( true )

  /** 今回使うMapper */
  class WordCountMapper extends Mapper[LongWritable, Text, Text, LongWritable] {
    type Context = Mapper[LongWritable, Text, Text, LongWritable]#Context

    val tokenizer = Tokenizer.builder().userDictionary( getClass.getResourceAsStream( "user.dic" ) ).build()
    val one = new LongWritable( 1L )

    override def map( key: LongWritable, value: Text, context: Context ) {
      // 申し訳程度にNFKCでUnicode正規化しとく
      val text = Normalizer.normalize( value.toString, Normalizer.Form.NFKC )

      // Kuromojiを使って形態素解析
      for ( token <- tokenizer.tokenize( text ).iterator() ) {
        // 助詞と接続詞は省いてみる
        val features = token.getAllFeatures()
        if ( !features.startsWith( "助詞" ) && !features.startsWith( "接続詞" ) ) {
          context.write( new Text( token.getSurfaceForm() ), one )
        }
      }
    }
  }

  /** 今回使うReducer */
  class WordCountReducer extends Reducer[Text, LongWritable, Text, LongWritable] {
    type Context = Reducer[Text, LongWritable, Text, LongWritable]#Context

    override def reduce( key: Text, values: java.lang.Iterable[LongWritable], context: Context ) {
      var sum = 0L
      for ( l <- values.iterator() )
        sum += l.get

        // 全部吐くと多いから5件以上出現する単語だけにしてみる
      if ( sum >= 5 ) context.write( key, new LongWritable( sum ) )
    }
  }
}