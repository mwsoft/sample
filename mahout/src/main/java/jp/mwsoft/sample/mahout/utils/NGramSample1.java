package jp.mwsoft.sample.mahout.utils;

import java.util.List;

import org.apache.mahout.common.nlp.NGrams;

public class NGramSample1 {
	public static void main(String[] args) {
		NGrams ngrams = new NGrams("i am a pen. you are a dog.", 2);

		List<String> list = ngrams.generateNGramsWithoutLabel();
		for( String gram : list )
		    System.out.println(gram);
	}
}
