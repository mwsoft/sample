package jp.mwsoft.sample.lucene.gosen;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import net.java.sen.SenFactory;
import net.java.sen.StreamTagger;
import net.java.sen.StringTagger;
import net.java.sen.dictionary.Token;

public class StreamTaggerSample {

	public static void main(String[] args) throws IOException {

		StringTagger stringTagger = SenFactory.getStringTagger(null);
		Reader reader = new InputStreamReader(new FileInputStream("shayo.txt"), "shift_jis");
		StreamTagger tagger = new StreamTagger(stringTagger, reader);

		while (tagger.hasNext()) {
			Token token = tagger.next();
			System.out.println(token.getSurface());
		}

	}

}
