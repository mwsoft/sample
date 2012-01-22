package jp.mwsoft.sample.rome;

import java.net.URL;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.impl.FeedFetcherCache;
import com.sun.syndication.fetcher.impl.HashMapFeedInfoCache;
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;

public class RomeFetcherSample {

	public static void main(String[] args) throws Exception {
		FeedFetcherCache feedInfoCache = HashMapFeedInfoCache.getInstance();
		HttpURLFeedFetcher feedFetcher = new HttpURLFeedFetcher(feedInfoCache);
		SyndFeed feed1 = feedFetcher.retrieveFeed(new URL("http://rss.rssad.jp/rss/gihyo/feed/rss1"));
		System.out.println(feed1.hashCode());
		Thread.sleep(1000);
		SyndFeed feed2 = feedFetcher.retrieveFeed(new URL("http://rss.rssad.jp/rss/gihyo/feed/rss1"));
		System.out.println(feed2.hashCode());
	}
}
