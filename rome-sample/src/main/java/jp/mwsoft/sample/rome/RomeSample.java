package jp.mwsoft.sample.rome;

import java.net.URL;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class RomeSample {
    public static void main(String[] args) throws Exception {
    	// String url = "http://rss.rssad.jp/rss/gihyo/feed/rss1";
    	// String url = "http://rss.rssad.jp/rss/gihyo/feed/rss2";
        String url = "http://rss.rssad.jp/rss/gihyo/feed/atom";

		URL feedUrl = new URL(url);
        SyndFeedInput input = new SyndFeedInput();

        SyndFeed feed = input.build(new XmlReader(feedUrl));
        // ブログのタイトル
        System.out.println(feed.getTitle());
        // ブログのURL
        System.out.println(feed.getLink());

        for (Object obj : feed.getEntries()) {
            SyndEntry entry = (SyndEntry) obj;
            // 記事タイトル
            System.out.println(entry.getTitle());
            // 記事のURL
            System.out.println(entry.getLink());
            // 記事の詳細
            System.out.println(entry.getDescription().getValue());
        }
    }
}
