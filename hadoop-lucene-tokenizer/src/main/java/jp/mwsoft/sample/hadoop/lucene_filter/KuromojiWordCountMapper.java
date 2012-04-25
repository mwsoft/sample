package jp.mwsoft.sample.hadoop.lucene_filter;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

public class KuromojiWordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

	Text text = new Text();
	LongWritable one = new LongWritable(1L);

	Tokenizer tokenizer = Tokenizer.builder().build();

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		List<Token> tokens = tokenizer.tokenize(value.toString());
		for (Token token : tokens) {
			text.set(token.getSurfaceForm());
			// text.set(word.toLowerCase());
			context.write(text, one);
		}
	}
}
