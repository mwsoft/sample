package jp.mwsoft.sample.twitter4j;

import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class StreamingSample {

	public static void main(String[] args) {
		// 認証キーを設定
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey("xxxxxxxxxxxxxxxxxxxx");
		builder.setOAuthConsumerSecret("xxxxxxxxxxxxxxxxxxxx");
		builder.setOAuthAccessToken("xxxxxxxxxxxxxxxxxxxx");
		builder.setOAuthAccessTokenSecret("xxxxxxxxxxxxxxxxxxxx");

		// Configurationを作る
		Configuration conf = builder.build();

		// TwitterStreamのインスタンス作成
		TwitterStream twitterStream = new TwitterStreamFactory(conf).getInstance();

		// Listenerを登録
		twitterStream.addListener(new Listener());

		// 実行（この処理はエラーにならない限り終了しない）
		twitterStream.sample();
	}
}

/** Tweetを出力するだけのListener */
class Listener extends StatusAdapter {
	// Tweetを受け取るたびにこのメソッドが呼び出される
	public void onStatus(Status status) {
		System.out.println(status.getText());
	}
}