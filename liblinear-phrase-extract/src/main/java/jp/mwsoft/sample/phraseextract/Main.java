package jp.mwsoft.sample.phraseextract;

import java.io.File;

public class Main {

	public static void main(String[] args) throws Exception {

		String usage = "Usage: \n" + "  parse:   mvn exec:java -Dexec.args=\"parse file_name\"\n"
				+ "  train:   mvn exec:java -Dexec.args=\"train\" \n"
				+ "  predict: mvn exec:java -Dexec.args=\"predict file_name\"\n";

		if (args.length < 1) {
			System.out.println(usage);
			System.exit(-1);
		}

		// parse
		if (args.length > 1 && "parse".equals(args[0]) && new File(args[1]).exists()) {
			Tokenizer.main(args[1]);
		}
		// train
		else if ("train".equals(args[0])) {
			if (!new File("model").exists())
				new File("model").mkdir();
			Train.createTrainFile(new File("train").listFiles());
			Train.train();
		}
		// predict
		else if ("predict".equals(args[0]) && new File(args[1]).exists()) {
			Predict.predict(args[1]);
		} 
		// word extract
		else if("extract".equals(args[0]) && new File(args[1]).exists()) {
			Predict.printKeywords(args[1]);
		}
		else {
			System.out.println(usage);
			System.exit(-1);
		}
	}
}
