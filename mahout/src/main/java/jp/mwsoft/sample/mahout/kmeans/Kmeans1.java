package jp.mwsoft.sample.mahout.kmeans;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;

public class Kmeans1 {

	public static void main(String[] args) throws Exception {

		KMeansDriver.run(new Configuration(),
				new Path("data/kmeans-test-vector"),
				new Path("data/kmeans-test-cluster"),
				new Path("data/kmeans-output"),
				new EuclideanDistanceMeasure(),
				0.001, 10, true, 0.0, false);
	}

}
