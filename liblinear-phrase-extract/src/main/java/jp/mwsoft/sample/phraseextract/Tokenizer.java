package jp.mwsoft.sample.phraseextract;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import org.atilika.kuromoji.Token;

/** 形態素解析して結果をファイル出力する */
public class Tokenizer {

	public static void main(String[] args) throws Exception {

		if (args.length < 1 || !new File(args[0]).exists()) {
			String usage = "Usage: java -cp liblinear-sample.jar %s filename";
			System.out.println(String.format(usage, Tokenizer.class.getName()));
			System.exit(-1);
		}

		parse(args[0]);
	}

	public static void parse(String path) throws IOException {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(path + ".out"));
			for (Term term : parseFile(path))
				writer.write(term.toString() + "\n");
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	public static List<Term> parseFile(String path) throws IOException {
		List<Term> terms = new ArrayList<Term>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(path));
			String line;
			while ((line = reader.readLine()) != null) {
				for (Term term : parseLine(line))
					terms.add(term);
			}
		} catch (IOException e) {
			if (reader != null)
				reader.close();
		}
		return terms;
	}

	public static List<Term> parseLine(String line) {
		line = Normalizer.normalize(line, Normalizer.Form.NFKC);

		List<Term> terms = new ArrayList<Term>();

		org.atilika.kuromoji.Tokenizer tokenizer = org.atilika.kuromoji.Tokenizer.builder().build();
		List<Token> tokens = tokenizer.tokenize(line);
		for (Token token : tokens) {
			Term term = new Term(token.getSurfaceForm(), token.getPartOfSpeech(), token.isUnknown());
			terms.add(term);
		}

		return terms;
	}
}
