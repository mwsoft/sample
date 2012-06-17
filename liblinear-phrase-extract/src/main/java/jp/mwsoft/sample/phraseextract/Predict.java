package jp.mwsoft.sample.phraseextract;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.bwaldvogel.liblinear.FeatureNode;
import de.bwaldvogel.liblinear.Linear;
import de.bwaldvogel.liblinear.Model;

public class Predict {

	/** predictを実行し、抽出したキーワードを出力する */
	public static void printKeywords(String path) throws IOException {
		// train形式の行を生成
		List<Term> terms = getTerms(path);

		// Modelのロード
		Model model = Linear.loadModel(new File("model/model"));

		// 結果推定 - 出力
		int preValue = 0;
		for (Term term : terms) {
			predictLine(term, model);
			if (term.getValue() == 1)
				System.out.print(term.getSurface() + " ");
			else if (preValue == 1)
				System.out.print(term.getSurface() + "\n");
			preValue = term.getValue();
		}
	}

	/** predictを実行し結果をファイルに出力する */
	public static void predict(String path) throws IOException {
		// train形式の行を生成
		List<Term> terms = getTerms(path);

		// Modelのロード
		Model model = Linear.loadModel(new File("model/model"));

		// 推定を行ない、結果をファイルに出力
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(path + ".result"));
			for (Term term : terms) {
				predictLine(term, model);
				writer.write(term.toString() + "\n");
			}
		} finally {
			writer.close();
		}
	}

	/** Termに変換する */
	public static List<Term> getTerms(String path) throws IOException {
		// 対象ファイルを形態素解析にかけ、Termに変換
		List<Term> terms = new ArrayList<Term>();
		for (Term term : Tokenizer.tokenizeFile(path))
			terms.add(Train.getTerm(term.toString()));
		Train.setTrainLine(terms);
		return terms;
	}

	/** 1行分の解析を行う */
	public static void predictLine(Term term, Model model) {
		String line = term.getTrainLine();
		List<FeatureNode> nodes = new ArrayList<FeatureNode>();
		for (String param : line.split(" ")) {
			String[] c = param.split(":");
			if (c.length != 2)
				continue;
			nodes.add(new FeatureNode(Integer.parseInt(c[0]), Double.parseDouble(c[1])));
		}
		term.setValue(Linear.predict(model, (FeatureNode[]) nodes.toArray(new FeatureNode[0])));
	}
}
