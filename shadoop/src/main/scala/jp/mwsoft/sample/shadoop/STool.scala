package jp.mwsoft.sample.shadoop

import org.apache.hadoop.conf.{ Configured, Configuration }
import org.apache.hadoop.fs.{ Path }
import org.apache.hadoop.mapreduce.{ Job }
import org.apache.hadoop.mapreduce.lib.input.{ FileInputFormat, TextInputFormat }
import org.apache.hadoop.mapreduce.lib.output.{ FileOutputFormat, TextOutputFormat }
import org.apache.hadoop.util.{ Tool, ToolRunner }

trait STool extends Configured with Tool {

  def main( args: Array[String] ) {
    exit( ToolRunner.run( this, args ) )
  }

  val mapper: SMapper[_, _, _, _]

  val reducer: SReducer[_, _, _, _] = null

  val combiner: SReducer[_, _, _, _] = null

  def getJob( jobName: String, inputPath: String, outputPath: String, conf: Configuration = getConf() ): Job = {
    val job = new Job( conf, jobName )

    FileInputFormat.setInputPaths( job, inputPath )
    FileOutputFormat.setOutputPath( job, new Path( outputPath ) )

    job.setJarByClass( mapper.getClass() )
    job.setMapperClass( mapper.getClass() )

    if ( reducer != null ) {
      job.setOutputKeyClass( reducer.outputKeyClass )
      job.setOutputValueClass( reducer.outputValueClass )
      job.setReducerClass( reducer.getClass() )
    }
    else {
      job.setOutputKeyClass( mapper.outputKeyClass )
      job.setOutputValueClass( mapper.outputValueClass )
    }

    if ( combiner != null ) {
      job.setCombinerClass( combiner.getClass() )
    }

    setInputFormat( job )
    setOutputFormat( job )

    job
  }

  def setInputFormat( job: Job ) = job.setInputFormatClass( classOf[TextInputFormat] )

  def setOutputFormat( job: Job ) =
    job.setOutputFormatClass( classOf[TextOutputFormat[_, _]] )

  def run( job: Job ): Int = {
    val success = job.waitForCompletion( true )
    if ( success ) 0 else 1
  }
}
