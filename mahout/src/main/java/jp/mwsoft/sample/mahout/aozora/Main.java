package jp.mwsoft.sample.mahout.aozora;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws Exception {

		Map<String, Long> bookIDMap = new HashMap<>();
		Map<String, Long> ngramIDMap = new HashMap<>();
		
		// @TODO convert vector, write sequence file

		File[] files = new File("data/aozora").listFiles();
		for (File file : files) {
			// get long book id
			bookIDMap.put(file.getName(), (long) bookIDMap.size());

			// get top n ngram
			Map<String, Double> map = TopNHiraganaNGram.topNHiraganaGram(file, 200);

			// get ngram id
			for (String key : map.keySet()) {
				if (!ngramIDMap.containsKey(key)) ngramIDMap.put(key, (long) ngramIDMap.size());
				long ngramID = ngramIDMap.get(key);
			}
		}
	}
}
