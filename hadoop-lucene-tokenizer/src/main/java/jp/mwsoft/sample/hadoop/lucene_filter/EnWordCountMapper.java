package jp.mwsoft.sample.hadoop.lucene_filter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.lucene.analysis.ASCIIFoldingFilter;
import org.apache.lucene.analysis.LengthFilter;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.KStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class EnWordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

	Text text = new Text();
	LongWritable one = new LongWritable(1L);

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		// TextをReaderに変換
		Reader reader = new StringReader(value.toString());

		// StandardAnalyzerで解析
		TokenStream stream = new StandardTokenizer(Version.LUCENE_36, reader);

		// ASCIIFoldingFilterでExtended Latinな文字や全角文字をLatin1な文字に変換
		stream = new ASCIIFoldingFilter(stream);

		// LowerCaseFilterで小文字に統一
		stream = new LowerCaseFilter(Version.LUCENE_36, stream);

		// StopFilterで一般的な単語は除外
		stream = new StopFilter(Version.LUCENE_36, stream, StandardAnalyzer.STOP_WORDS_SET);

		// LengthFilterで2文字〜20文字の文字列以外は除外（あまり長い文字列はいらないよね）
		stream = new LengthFilter(false, stream, 2, 20);

		// EnglishPossessiveFilterで「's」表記を除外
		stream = new EnglishPossessiveFilter(Version.LUCENE_36, stream);

		// KStemFilterを使って複数形とかの揺れを吸収
		stream = new KStemFilter(stream);

		// 結果を出力
		while (stream.incrementToken()) {
			CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
			text.set(term.toString());
			context.write(text, one);
		}

		stream.close();
		reader.close();
	}
}
