package jp.mwsoft.sample.mahout.utils;

import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ngram.NGramTokenFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class NGramSample4 {
	public static void main(String[] args) throws Exception {

		StringReader reader = new StringReader("私はペンです・・・よね？");

		TokenStream stream = new SymbolTokenizer(Version.LUCENE_36, reader);

		stream = new NGramTokenFilter(stream, 2, 3);

		while (stream.incrementToken()) {
			CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
			System.out.println(term.toString());
		}

	}
}
