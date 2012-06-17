package jp.mwsoft.sample.phraseextract;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FeatureId {

	private static Map<String, Integer> featureMap = null;

	public static int getFeatureId(String feature) {
		if (featureMap == null) {
			try {
				featureMap = new ConcurrentHashMap<String, Integer>();
				BufferedReader reader = new BufferedReader(new FileReader("feature_id"));
				String line;
				while ((line = reader.readLine()) != null) {
					String[] params = line.split("\t");
					featureMap.put(params[1], Integer.parseInt(params[0]));
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		if(!featureMap.containsKey(feature))
			throw new RuntimeException( "feature_id " + feature + " not found. add fetture_id" );
			
		return featureMap.get(feature);
	}

}
