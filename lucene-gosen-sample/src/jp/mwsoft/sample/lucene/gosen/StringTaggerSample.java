package jp.mwsoft.sample.lucene.gosen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.java.sen.SenFactory;
import net.java.sen.StringTagger;
import net.java.sen.dictionary.Token;

public class StringTaggerSample {

	public static void main(String[] args) throws IOException {

		// この3行で解析できる
		StringTagger tagger = SenFactory.getStringTagger(null);
		List<Token> tokens = new ArrayList<Token>();
		tagger.analyze("もう眠い", tokens);

		// 解析結果の中身をいろいろ出力してみる
		for (Token token : tokens) {
			System.out.println("==============================");
			System.out.println("surface : " + token.getSurface());
			System.out.println("cost : " + token.getCost());
			System.out.println("length : " + token.getLength());
			System.out.println("start : " + token.getStart());
			System.out.println("additionalInformation : " + token.getMorpheme().getAdditionalInformation());
			System.out.println("basicForm : " + token.getMorpheme().getBasicForm());
			System.out.println("conjugationalForm : " + token.getMorpheme().getConjugationalForm());
			System.out.println("conjugationalType : " + token.getMorpheme().getConjugationalType());
			System.out.println("partOfSpeech : " + token.getMorpheme().getPartOfSpeech());
			System.out.println("pronunciations : " + token.getMorpheme().getPronunciations());
			System.out.println("readings : " + token.getMorpheme().getReadings());
		}

	}
}
