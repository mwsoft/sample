package jp.mwsoft.sample.rome;

import java.net.URL;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class SyncEntrySample {

	public static void main(String[] args) throws Exception {
		URL feedUrl = new URL("http://blog.mwsoft.jp/index.rdf");
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = input.build(new XmlReader(feedUrl));

		SyndEntry entry = (SyndEntry) feed.getEntries().get(0);
		System.out.println("author : " + entry.getAuthor());
		System.out.println("link : " + entry.getLink());
		System.out.println("title : " + entry.getTitle());
		System.out.println("uri : " + entry.getUri());
		SyndContent contents = (SyndContent)entry.getContents().get(0);
		System.out.println("contentType : " + contents.getType());
		System.out.println("contentValue : " + contents.getValue());
		System.out.println("contributors : " + entry.getContributors());
		SyndContent description = (SyndContent)entry.getDescription();
		System.out.println("descriptionType : " + description.getType());
		System.out.println("descriptionValue : " + description.getValue());
		System.out.println("enclosures : " + entry.getEnclosures());
		System.out.println("links : " + entry.getLinks());
		System.out.println("publishedDate : " + entry.getPublishedDate());
		System.out.println("updatedDate : " + entry.getUpdatedDate());
	}
}
