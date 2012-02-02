package jp.mwsoft.sample.kuromoji;

import java.io.IOException;

import org.atilika.kuromoji.util.DictionaryBuilder;
import org.atilika.kuromoji.util.DictionaryBuilder.DictionaryFormat;

public class CreateUniDic {

	public static void main(String[] args) throws IOException {

		DictionaryBuilder builder = new DictionaryBuilder();

		builder.build(DictionaryFormat.UNIDIC, "/home/masato/Downloads/unidic-1.3.12-pkg/unidic-mecab-1.3.12/", "out",
				"utf-8", false);

	}
}
