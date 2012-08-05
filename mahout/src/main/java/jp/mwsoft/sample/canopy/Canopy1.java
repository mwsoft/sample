package jp.mwsoft.sample.canopy;

import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;

public class Canopy1 {

	public static void main(String[] args) throws Exception {
		
		CanopyDriver.run(new Path("data/canopy-sample-vector"), 
				new Path("data/canopy-output2"), 
				new EuclideanDistanceMeasure(),
				2.0, 4.0, 
				true, 
				0.0,
				false);
		
	}
}
