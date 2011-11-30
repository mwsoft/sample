package jp.mwsoft.sample.shadoop

import org.apache.hadoop.conf.{ Configured, Configuration }
import org.apache.hadoop.fs.{ Path }
import org.apache.hadoop.mapreduce.{ Job }
import org.apache.hadoop.mapreduce.lib.input.{ FileInputFormat, TextInputFormat }
import org.apache.hadoop.mapreduce.lib.output.{ FileOutputFormat, TextOutputFormat }
import org.apache.hadoop.util.{ Tool, ToolRunner }

trait STool extends Configured with Tool {

  /** 標準のmainメソッドも用意しておく */
  def main(args: Array[String]) {
    exit(ToolRunner.run(this, args))
  }

  /** 継承先のクラスはClassはMapperを設定すること */
  val mapper: SMapper[_, _, _, _]

  /** 任意でReducerを設定 */
  val reducer: SReducer[_, _, _, _] = null

  /** 任意でCombinerを設定 */
  val combiner: SReducer[_, _, _, _] = null

  // Comparetorとかも設定できるようにしようかな

  /** 設定されたMapperやReducerの情報を元にいろいろ設定したJobを返す */
  def getJob(jobName: String, inputPath: String, outputPath: String, conf: Configuration = getConf()): Job = {
    val job = new Job(conf, jobName)

    FileInputFormat.setInputPaths(job, inputPath)
    FileOutputFormat.setOutputPath(job, new Path(outputPath))

    job.setJarByClass(mapper.getClass())
    job.setMapperClass(mapper.getClass())

    if (reducer != null) {
      job.setOutputKeyClass(reducer.outputKeyClass)
      job.setOutputValueClass(reducer.outputValueClass)
      job.setReducerClass(reducer.getClass())
    } else {
      job.setOutputKeyClass(mapper.outputKeyClass)
      job.setOutputValueClass(mapper.outputValueClass)
    }

    if (combiner != null) {
      job.setCombinerClass(combiner.getClass())
    }

    setInputFormat(job)
    setOutputFormat(job)

    job
  }

  /** setInputFormatClassを実行 */
  def setInputFormat(job: Job) = job.setInputFormatClass(classOf[TextInputFormat])

  /** setOutputFormatClassを実行 */
  def setOutputFormat(job: Job) =
    job.setOutputFormatClass(classOf[TextOutputFormat[_, _]])

  /** Jobの実行。成功したら0、失敗したら1を返す。 */
  def run(job: Job): Int = {
    val success = job.waitForCompletion(true)
    if (success) 0 else 1
  }

  /** Jobの完了を待つ。成功したら0、失敗したら1を返す。 */
  def waitFor(job: Job, termSec: Int = 30): Int = {
    while (job.isComplete()) {
      Thread.sleep(termSec * 1000)
    }
    if (job.isSuccessful()) 0 else 1
  }
}
