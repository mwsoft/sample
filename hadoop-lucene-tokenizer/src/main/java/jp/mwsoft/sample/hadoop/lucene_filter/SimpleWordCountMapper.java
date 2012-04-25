package jp.mwsoft.sample.hadoop.lucene_filter;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SimpleWordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

	Text text = new Text();
	LongWritable one = new LongWritable(1L);

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String[] words = value.toString().split("\\s");
		for (String word : words) {
			text.set(word);
			//text.set(word.toLowerCase());
			context.write(text, one);
		}
	}
}
