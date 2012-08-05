package jp.mwsoft.sample.mahout.kmeans;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.mahout.clustering.classify.WeightedVectorWritable;

public class Kmeans1Disp {

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);

		try (SequenceFile.Reader reader = new SequenceFile.Reader(fs,
				new Path("data/kmeans-output/clusteredPoints/part-m-00000"), conf)) {
			IntWritable key = new IntWritable();
			WeightedVectorWritable value = new WeightedVectorWritable();
			while (reader.next(key, value)) {
				System.out.println("point=" + value.getVector() + "  cluster=" + key.get());
			}
		}

	}

}
