package jp.mwsoft.sample.twitter4j;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class UserSearch {

	public static void main(String[] args) throws TwitterException {
		// 初期化
		Twitter twitter = new TwitterFactory().getInstance();
		Query query = new Query();

		// 検索ワードをセット（hadoopを含む日本語Tweet）
		query.setQuery("from:faridyu OR to:faridyu");

		// 検索して結果を表示
		QueryResult result = twitter.search(query);
		for (Tweet tweet : result.getTweets())
			System.out.println(tweet.getText());
	}

}
