package jp.mwsoft.sample.phraseextract;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.bwaldvogel.liblinear.InvalidInputDataException;
import de.bwaldvogel.liblinear.Linear;
import de.bwaldvogel.liblinear.Model;
import de.bwaldvogel.liblinear.Parameter;
import de.bwaldvogel.liblinear.Problem;
import de.bwaldvogel.liblinear.SolverType;

public class Train {

	public static void main(String[] args) throws Exception {

		if (!new File("model").exists())
			new File("model").mkdir();

		// 対象ファイルたちからliblinear用のファイルを作る
		createTrainFile(new File("train").listFiles());

		// trainする
		train();
	}

	/** liblinearのtrainを実行 */
	public static void train() throws IOException, InvalidInputDataException {
		Parameter param = new Parameter(SolverType.L2R_L2LOSS_SVC_DUAL, 1, Double.POSITIVE_INFINITY);
		Problem prob = Problem.readFromFile(new File("model/model.txt"), -1.0);

		Model model = Linear.train(prob, param);
		model.save(new File("model/model"));
	}

	/** trainを実行するためのファイルを用意する */
	public static void createTrainFile(File[] fileList) throws IOException {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("model/model.txt"));
			for (File file : fileList) {
				List<Term> terms = getTerms(file.getPath());
				setTrainLine(terms);
				for (Term term : terms)
					writer.write(term.getTrainLine() + "\n");
			}
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	/** ファイルからTermに変換 */
	public static List<Term> getTerms(String path) throws IOException {
		// 学習結果ファイルを読み込んで、Listに詰め込む
		List<Term> terms = new ArrayList<Term>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(path));
			String line;
			while ((line = reader.readLine()) != null) {
				Term term = getTerm(line);
				if (term != null)
					terms.add(term);
			}
		} finally {
			if (reader != null)
				reader.close();
		}
		return terms;
	}

	/** 1行データをTermに変換 */
	public static Term getTerm(String line) {
		String[] param = line.split("\t", -1);
		if (param.length < 4)
			return null;
		return new Term(Integer.parseInt(param[0]), param[1], param[2], "1".equals(param[3]));
	}

	/** 個別の学習用ファイルをtrain用に変換し、結果をList（中身は各行）を返す */
	public static void setTrainLine(List<Term> terms) throws IOException {
		// 各termの前後関係を含めて
		for (int i = 0; i < terms.size(); i++) {
			StringBuilder buf = new StringBuilder();
			buf.append(terms.get(i).getValue());
			// 続く5つの形態素の情報を記述する
			for (int j = 0; j < 5; j++) {
				if (terms.size() - 1 <= i + j)
					break;
				buf.append(" " + terms.get(i + j).toTrainString(j * 100 + 1));
			}
			// 1つ手前の形態素の情報も記述する
			if (i - 1 > 0)
				buf.append(" " + terms.get(i - 1).toTrainString(500 + 1));
			terms.get(i).setTrainLine(buf.toString());
		}
	}
}
