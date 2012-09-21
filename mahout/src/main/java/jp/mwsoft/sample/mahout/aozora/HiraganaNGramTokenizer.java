package jp.mwsoft.sample.mahout.aozora;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.CharTokenizer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ngram.NGramTokenFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class HiraganaNGramTokenizer extends CharTokenizer {

	public static List<String> ngram(String str, int n1, int n2) throws IOException {

		StringReader reader = new StringReader(str);

		TokenStream stream = new HiraganaNGramTokenizer(Version.LUCENE_36, reader);
		stream = new NGramTokenFilter(stream, n1, n2);

		List<String> list = new ArrayList<>();
		while (stream.incrementToken()) {
			CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
			list.add(term.toString());
		}
		
		return list;
	}

	public HiraganaNGramTokenizer(Version version, Reader input) {
		super(version, input);
	}

	protected boolean isTokenChar(int c) {
		return UnicodeBlock.of(c) == UnicodeBlock.HIRAGANA;
	}
}