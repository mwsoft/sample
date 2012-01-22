package jp.mwsoft.sample.rome;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class MyCacheSample {

	public static void main(String[] args) throws Exception {
		String url = "http://blog.mwsoft.jp/index.rdf";
		URL feedUrl = new URL(url);
		SyndFeedInput input = new SyndFeedInput();
		InputStream is = openConnection(feedUrl).getInputStream();
		XmlReader reader = new XmlReader(is);
		SyndFeed feed = input.build(reader);
		reader.close();
		openConnection(feedUrl);
	}

	// キャッシュ
	static Map<URL, CacheInfo> cache = new ConcurrentHashMap<URL, CacheInfo>();

	public static URLConnection openConnection(URL url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		// set If-Modifed-Since and If-None-Match
		CacheInfo info = cache.get(url);
		if (info != null) {
			if (info.getLastModified() > 0)
				conn.setIfModifiedSince(info.getLastModified());
			if (info.getEtag() != null)
				conn.setRequestProperty("If-None-Match", info.getEtag());
		}

		conn.connect();

		// result not 2xx return null
		if (conn.getResponseCode() > 300) {
			conn.getInputStream().close();
			System.out.println(conn.getResponseCode());
			return null;
		}

		// set CacheInfo
		Map<String, List<String>> headers = conn.getHeaderFields();
		CacheInfo newInfo = new CacheInfo();
		long lastModified = conn.getLastModified();
		if (lastModified > 0)
			newInfo.setLastModified(lastModified);
		List<String> etag = headers.get("Etag");
		if (etag != null && etag.size() > 0)
			newInfo.setEtag(etag.get(0));
		synchronized (cache) {
			cache.put(url, newInfo);
		}

		return conn;
	}
}

class CacheInfo {
	private long lastModified = -1;
	private String etag;

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

}
