package jp.mwsoft.sample.similarpage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class HtmlParser {

	public static final List<String> excludeTags = Arrays.asList("script", "style", "meta", "title");
	public static final HtmlCleaner cleaner = new HtmlCleaner();

	URL url;
	String text = "";
	String title = "";
	List<String> links = new ArrayList<String>();

	int responseCode = -1;

	public HtmlParser(String url) throws IOException, MalformedURLException {
		parse(new URL(url));
	}

	public HtmlParser(URL url) throws IOException {
		parse(url);
	}

	// get content text from url
	public void parse(URL url) {
		this.url = url;

		InputStream is = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			responseCode = conn.getResponseCode();
			is = conn.getInputStream();
			TagNode rootNode = cleaner.clean(is);
			is.close();

			// remove script/style elements
			for (TagNode node : rootNode.getAllElements(true)) {
				String nodeName = node.getName().toLowerCase();
				if (nodeName.equals("a")) {
					String href = node.getAttributeByName("href");
					if (href != null)
						links.add(href);
				}
				if (nodeName.equals("title"))
					this.title = node.getText().toString();
				if (excludeTags.contains(nodeName))
					node.getParent().removeChild(node);
			}

			this.text = rootNode.getText().toString().trim();
		} catch (IOException e) {
		}
	}
}
