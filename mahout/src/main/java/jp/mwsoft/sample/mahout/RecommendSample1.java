package jp.mwsoft.sample.mahout;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;

public class RecommendSample1 {

	public static void main(String[] args) throws Exception {

		DataModel model = new FileDataModel(new File("data/recommend_sample1.csv"));

		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighbor = new NearestNUserNeighborhood(3, similarity, model);

		Recommender recommender = new GenericUserBasedRecommender(model, neighbor, similarity);
		List<RecommendedItem> items = recommender.recommend(4, 1);

		for (RecommendedItem item : items) {
			System.out.println(item);
		}

	}

}
