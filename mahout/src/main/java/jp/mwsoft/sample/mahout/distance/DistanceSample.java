package jp.mwsoft.sample.mahout.distance;

import org.apache.mahout.common.distance.ChebyshevDistanceMeasure;
import org.apache.mahout.common.distance.CosineDistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.common.distance.ManhattanDistanceMeasure;
import org.apache.mahout.common.distance.MinkowskiDistanceMeasure;
import org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure;
import org.apache.mahout.common.distance.TanimotoDistanceMeasure;
import org.apache.mahout.common.distance.WeightedDistanceMeasure;
import org.apache.mahout.common.distance.WeightedEuclideanDistanceMeasure;
import org.apache.mahout.common.distance.WeightedManhattanDistanceMeasure;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;

public class DistanceSample {

	public static void main(String[] args) {

		double[] d1 = { 1.0, 1.0 };
		double[] d2 = { 3.0, 2.0 };

		Vector v1 = new RandomAccessSparseVector(2);
		v1.assign(d1);
		Vector v2 = new RandomAccessSparseVector(2);
		v2.assign(d2);

		// マンハッタンの夜景をあなたに
		double distance = new ManhattanDistanceMeasure().distance(v1, v2);
		System.out.println(distance);

		// ゆーくり
		distance = new EuclideanDistanceMeasure().distance(v1, v2);
		System.out.println(distance);

		// すくえあゆーくり
		distance = new SquaredEuclideanDistanceMeasure().distance(v1, v2);
		System.out.println(distance);

		// 重み付きゆーくり
		Vector weightVector = new RandomAccessSparseVector(2);
		double[] weight = { 1.0, 2.0 };
		weightVector.assign(weight);
		WeightedDistanceMeasure wMeasure = new WeightedEuclideanDistanceMeasure();
		wMeasure.setWeights(weightVector);
		distance = wMeasure.distance(v1, v2);
		System.out.println(distance);

		wMeasure = new WeightedManhattanDistanceMeasure();
		wMeasure.setWeights(weightVector);
		distance = wMeasure.distance(v1, v2);
		System.out.println(distance);

		// こさいん
		double[] d3 = { 6.0, 4.0 };
		Vector v3 = new RandomAccessSparseVector(2);
		v3.assign(d3);
		double distance1 = new CosineDistanceMeasure().distance(v1, v2);
		double distance2 = new CosineDistanceMeasure().distance(v1, v3);
		System.out.println("d1=" + distance1 + ", d2=" + distance2);

		// ちぇびしぇふ
		distance = new ChebyshevDistanceMeasure().distance(v1, v2);
		System.out.println(distance);

		// みんこふすき
		MinkowskiDistanceMeasure minkowDM = new MinkowskiDistanceMeasure();
		minkowDM.setExponent(1.0);
		distance = minkowDM.distance(v1, v2);
		System.out.println(distance);

		// たにもと
		TanimotoDistanceMeasure tanimotDM = new TanimotoDistanceMeasure();
		distance = tanimotDM.distance(v1, v2);
		System.out.println(distance);
	}
}
