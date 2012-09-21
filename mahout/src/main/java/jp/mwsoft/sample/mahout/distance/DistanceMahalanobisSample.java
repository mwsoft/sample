package jp.mwsoft.sample.mahout.distance;

import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.common.distance.MahalanobisDistanceMeasure;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.SparseMatrix;
import org.apache.mahout.math.Vector;

public class DistanceMahalanobisSample {

	public static void main(String[] args) {

		double[][] d = { { 1.0, 2.0 }, { 2.0, 4.0 },
				{ 3.0, 6.0 }, { 3.0, 2.0 }, { 4.0, 8.0 } };

		Vector v1 = new RandomAccessSparseVector(2);
		v1.assign(d[0]);
		Vector v2 = new RandomAccessSparseVector(2);
		v2.assign(d[1]);
		Vector v3 = new RandomAccessSparseVector(2);
		v3.assign(d[2]);
		Vector v4 = new RandomAccessSparseVector(2);
		v4.assign(d[3]);
		Vector v5 = new RandomAccessSparseVector(2);
		v5.assign(d[4]);

		Matrix matrix = new SparseMatrix(2, 2);
		matrix.assignRow(0, v1);
		matrix.assignRow(1, v2);

		EuclideanDistanceMeasure dmE = new EuclideanDistanceMeasure();
		double distance1 = dmE.distance(v2, v3);
		double distance2 = dmE.distance(v2, v4);
		System.out.println("d1=" + distance1 + ", d2=" + distance2);

		MahalanobisDistanceMeasure dmM = new MahalanobisDistanceMeasure();
		dmM.setInverseCovarianceMatrix(matrix);
		distance1 = dmM.distance(v2, v3);
		distance2 = dmM.distance(v2, v4);
		System.out.println("d1=" + distance1 + ", d2=" + distance2);
	}
}
