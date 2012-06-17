package jp.mwsoft.sample.phraseextract;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import org.atilika.kuromoji.Token;

/** 形態素解析して結果をファイル出力する */
public class Tokenizer {

	/** ファイルを形態素解析して、別ファイルに結果を出力 */
	public static void main(String path) throws IOException {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(path + ".out"));
			for (Term term : tokenizeFile(path))
				writer.write(term.toString() + "\n");
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	/** 指定ファイルを形態素解析し、結果をTermクラスに格納して返す */
	public static List<Term> tokenizeFile(String path) throws IOException {

		org.atilika.kuromoji.Tokenizer tokenizer = org.atilika.kuromoji.Tokenizer.builder().userDictionary("userdic")
				.build();

		List<Term> terms = new ArrayList<Term>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(path));
			String line;
			while ((line = reader.readLine()) != null) {
				for (Term term : tokenizeLine(line, tokenizer))
					terms.add(term);
			}
		} catch (IOException e) {
			if (reader != null)
				reader.close();
		}
		return terms;
	}

	/** 指定文字列を形態素解析し、結果をTermクラスに格納して返す */
	public static List<Term> tokenizeLine(String line, org.atilika.kuromoji.Tokenizer tokenizer) {
		line = Normalizer.normalize(line, Normalizer.Form.NFKC);

		List<Term> terms = new ArrayList<Term>();

		List<Token> tokens = tokenizer.tokenize(line);
		for (Token token : tokens) {
			Term term = new Term(token.getSurfaceForm(), token.getPartOfSpeech(), token.isUnknown());
			terms.add(term);
		}

		return terms;
	}
}
