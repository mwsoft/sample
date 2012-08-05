package jp.mwsoft.sample.mahout.utils;

import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKBigramFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class NGramSample3 {
	public static void main(String[] args) throws Exception {

		StringReader reader = new StringReader("私はペンです。");

		TokenStream stream = new StandardTokenizer(Version.LUCENE_36, reader);
		stream = new CJKBigramFilter(stream);

		while (stream.incrementToken()) {
			CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
			System.out.println(term.toString());
		}

	}
}
