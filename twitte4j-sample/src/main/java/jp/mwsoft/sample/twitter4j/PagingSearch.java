package jp.mwsoft.sample.twitter4j;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class PagingSearch {

	public static void main(String[] args) throws TwitterException, InterruptedException {
		// 初期化
		Twitter twitter = new TwitterFactory().getInstance();
		Query query = new Query();

		// 検索ワードをセット（試しにhttpを検索）
		query.setQuery("http");

		// 1度のリクエストで取得するTweetの数（100が最大）
		query.setRpp(100);

		// 最大1500件（15ページ）なので15回ループ
		for (int i = 1; i <= 15; i++) {
			// ページ指定
			query.setPage(i);
			// 検索実行
			QueryResult result = twitter.search(query);
			// 取ったテキストを表示
			for (Tweet tweet : result.getTweets()) {
				System.out.println(tweet.getText());
			}
			System.out.println(result.getTweets().size());
			// たまに次ページあるのに100件取れないことがあるから
			// 95件以上あったら次ページを確認しに行くことにする
			if (result.getTweets().size() < 95)
				break;
			// 連続でリクエストすると怒られるので3秒起きにしておく
			Thread.sleep(3000);
		}
	}

}
