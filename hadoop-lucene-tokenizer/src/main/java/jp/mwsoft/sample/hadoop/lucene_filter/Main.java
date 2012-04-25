package jp.mwsoft.sample.hadoop.lucene_filter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.reduce.LongSumReducer;

public class Main {

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration(true);
		Job job = new Job(conf, "wordcount");

		FileInputFormat.setInputPaths(job, new Path("data/in/tweet_half.txt"));
		//FileInputFormat.setInputPaths(job, new Path("data/in/en-abstract.txt"));
		FileOutputFormat.setOutputPath(job, new Path("data/out/" + System.currentTimeMillis()));

		job.setJarByClass(JaWordCountMapper.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		job.setMapperClass(JaWordCountMapper.class);
		job.setCombinerClass(LongSumReducer.class);
		job.setReducerClass(LongSumReducer.class);

		job.waitForCompletion(true);
	}
}
