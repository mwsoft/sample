package jp.mwsoft.sample.lucene.gosen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.java.sen.SenFactory;
import net.java.sen.StringTagger;
import net.java.sen.dictionary.Token;

public class MultiDicSample {

	public static void main(String[] args) throws IOException {
		List<Token> tokens = new ArrayList<Token>();

		// ipadicを利用
		StringTagger ipaTagger = SenFactory.getStringTagger("dictionary/ipadic");
		System.out.println(ipaTagger.analyze("1984年", tokens));
		  //=> [1984, 年]
		
		// naist-chasenを利用
		StringTagger naistTagger = SenFactory.getStringTagger("dictionary/naist-chasen");
		System.out.println(naistTagger.analyze("1984年", tokens));
		  //=> [1, 9, 8, 4, 年]
	}

}
