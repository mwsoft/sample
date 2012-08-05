package jp.mwsoft.sample.canopy;

import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;

public class Kmeans1 {

	public static void main(String[] args) throws Exception {

		KMeansDriver.run(new Path("data/canopy-sample-vector"),
				new Path("data/canopy-output/clusters-0-final"),
				new Path("data/canopy-kmeans-output"),
				new EuclideanDistanceMeasure(),
				0.001, 10, true, 0.0, false);
	}

}
