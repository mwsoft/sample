package jp.mwsoft.sample.twitter4j;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class SimpleSearch {

	public static void main(String[] args) throws TwitterException {

		// 初期化
		Twitter twitter = new TwitterFactory().getInstance();
		Query query = new Query();

		// 検索ワードをセット（試しにバルスを検索）
		query.setQuery("バルス");

		// 検索実行
		QueryResult result = twitter.search(query);

		System.out.println("ヒット数 : " + result.getTweets().size());

		// 検索結果を見てみる
		for (Tweet tweet : result.getTweets()) {
			// 本文
			System.out.println(tweet.getText());
			// 発言したユーザ
			System.out.println(tweet.getFromUser());
			// 発言した日時
			System.out.println(tweet.getCreatedAt());
			// 他、取れる値はJavaDoc参照
			// http://twitter4j.org/ja/javadoc/twitter4j/Tweet.html
		}

	}

}
