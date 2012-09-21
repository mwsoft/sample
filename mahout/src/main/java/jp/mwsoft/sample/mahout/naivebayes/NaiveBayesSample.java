package jp.mwsoft.sample.mahout.naivebayes;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.classifier.naivebayes.ComplementaryNaiveBayesClassifier;
import org.apache.mahout.classifier.naivebayes.NaiveBayesModel;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;

public class NaiveBayesSample {

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();
		NaiveBayesModel model = NaiveBayesModel.materialize(new Path("."), conf);
		ComplementaryNaiveBayesClassifier classifier = new ComplementaryNaiveBayesClassifier(model);

		// サッカー:303, ゴール:302, ナビスコ:366,
		Vector vec = new RandomAccessSparseVector(1343);
		vec.setQuick(419, 1.0);
		vec.setQuick(417, 1.0);
		vec.setQuick(312, 1.0);
		vec.setQuick(416, 1.0);
		vec.setQuick(417, 1.0);
		vec.setQuick(460, 1.0);

		// 0:baseball, 1:f1, 2:soccer
		Vector result = classifier.classifyFull(vec);
		for (int i = 0; i < result.size(); i++)
			System.out.println(i + ", " + result.get(i));
	}
}
