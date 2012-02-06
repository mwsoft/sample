package jp.mwsoft.sample.twitter4j;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class GeoSearch {

	public static void main(String[] args) throws TwitterException {
		// 初期化
		Twitter twitter = new TwitterFactory().getInstance();
		Query query = new Query();

		// 緯度経度を使って新宿区役所あたりから10km四方を設定（IPアドレスでも指定できるらしい）
		GeoLocation geo = new GeoLocation(35.69384, 139.703549);
		query.setGeoCode(geo, 10.0, Query.KILOMETERS);

		// 検索実行
		QueryResult result = twitter.search(query);

		// これで新宿らへんのTweetがとれてるらしい（placeやgeoLocationはほぼ空のようだけど）
		for (Tweet tweet : result.getTweets()) {
			System.out.println(tweet.getText());
			System.out.println(tweet.getPlace() + " : " + tweet.getGeoLocation());
		}
	}
}
