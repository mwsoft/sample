package jp.mwsoft.sample.mahout.utils;

import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class NGramSample2 {
	public static void main(String[] args) throws Exception {

		StringReader reader = new StringReader("私はペンです。");
		
		// 2gramと3gramを出力する
		TokenStream stream = new NGramTokenizer(reader, 2, 3);

		while (stream.incrementToken()) {
			CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
			System.out.println(term.toString());
		}

	}
}
