package jp.mwsoft.sample.mahout.aozora;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TopNHiraganaNGram {

	public static Map<String, Double> topNHiraganaGram(File file, int n) throws IOException {
		// 2-3gram counter
		Map<String, Integer> counts = new HashMap<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				// get 2-3gram strings
				List<String> list = HiraganaNGramTokenizer.ngram(line, 2, 3);

				// convert id and count
				for (String str : list) {
					Integer count = counts.get(str);
					counts.put(str, count == null ? 1 : count + 1);
				}
			}
		}

		// osrt
		List<Map.Entry<String, Integer>> list = new LinkedList<>(counts.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
				return e1.getValue().compareTo(e2.getValue()) * -1;
			}
		});
		
		// create top n map
		double max = list.get(0).getValue();
		n = n >= list.size() ? n : list.size() - 1;
		Map<String, Double> topNMap = new HashMap<>();
		for (int i = 0; i < n; i++)
			topNMap.put(list.get(i).getKey(), list.get(i).getValue() / max);

		return topNMap;
	}

}
