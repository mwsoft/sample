package jp.mwsoft.sample.rome;

import java.net.URL;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class SyndFeedSample {
	public static void main(String[] args) throws Exception {
		URL feedUrl = new URL("http://headlines.yahoo.co.jp/rss/indonews_c_int.xml");
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = input.build(new XmlReader(feedUrl));

		System.out.println("author : " + feed.getAuthor());
		System.out.println("authors : " + feed.getAuthors());
		System.out.println("categories : " + feed.getCategories());
		System.out.println("contributors : " + feed.getContributors());
		System.out.println("description : " + feed.getDescription());
		System.out.println("encoding : " + feed.getEncoding());
		System.out.println("feedType : " + feed.getFeedType());
		System.out.println("imageTitle : " + feed.getImage().getTitle());
		System.out.println("imageLink : " + feed.getImage().getLink());
		System.out.println("imageUrl : " + feed.getImage().getUrl());
		System.out.println("language : " + feed.getLanguage());
		System.out.println("title : " + feed.getTitle());
		System.out.println("link : " + feed.getLink());
		System.out.println("uri : " + feed.getUri());
	}
}
