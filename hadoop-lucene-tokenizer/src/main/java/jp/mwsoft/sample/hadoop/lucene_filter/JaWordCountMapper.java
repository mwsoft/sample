package jp.mwsoft.sample.hadoop.lucene_filter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.lucene.analysis.LengthFilter;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.icu.ICUNormalizer2Filter;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.analysis.ja.JapaneseBaseFormFilter;
import org.apache.lucene.analysis.ja.JapaneseKatakanaStemFilter;
import org.apache.lucene.analysis.ja.JapanesePartOfSpeechStopFilter;
import org.apache.lucene.analysis.ja.JapaneseTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class JaWordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

	Text text = new Text();
	LongWritable one = new LongWritable(1L);

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		// TextをReaderに変換（toStringしてStringReader使った方が安全）
		Reader reader = new StringReader(value.toString());

		// JapaneseAnalyzerで解析（userdicはnull、ModeはNORMALを指定）
		TokenStream stream = new JapaneseTokenizer(reader, null, true, JapaneseTokenizer.Mode.NORMAL);

		// Unicode正規化
		stream = new ICUNormalizer2Filter(stream);

		// 小文字に統一
		stream = new LowerCaseFilter(Version.LUCENE_36, stream);

		// 一般的な単語は除外
		stream = new StopFilter(Version.LUCENE_36, stream, JapaneseAnalyzer.getDefaultStopSet());

		// 16文字以上の単語は除外（あまり長い文字列はいらないよね）
		stream = new LengthFilter(false, stream, 1, 16);

		// 動詞の活用を揃える（疲れた→疲れる）
		stream = new JapaneseBaseFormFilter(stream);

		// 助詞、助動詞、接続詞を対象外とする
		stream = new JapanesePartOfSpeechStopFilter(false, stream, JapaneseAnalyzer.getDefaultStopTags());

		// カタカナ長音の表記揺れを吸収
		stream = new JapaneseKatakanaStemFilter(stream);

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
