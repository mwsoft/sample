package jp.mwsoft.sample.similarpage;

import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

public class SolrInput {

	public static void main(String[] args) throws Exception {
		server = new CommonsHttpSolrServer("http://localhost:8983/solr");
		server.deleteByQuery("*:*");
		URL baseUrl = new URL("http://localhost/");
		Set<String> linkList = CreateLinkList.getPathList(baseUrl.toString());
		for (String link : linkList) {
			try {
				add(new URL(baseUrl, link));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		server.commit();
	}

	public static Set<String> urlSet = new HashSet<String>();
	public static Queue<String> urlList = new LinkedList<String>();
	public static CommonsHttpSolrServer server;;

	public static void add(URL url) throws Exception {
		HtmlParser parser = new HtmlParser(url);
		if (parser.responseCode == 200) {
			SolrInputDocument doc = new SolrInputDocument();
			doc.setField("path", parser.url.getPath());
			doc.setField("title", parser.title);
			doc.setField("content", parser.text);
			server.add(doc);
		}
	}

	public static boolean checkLink(String link) {
		return !link.startsWith("http") && !link.contains("#");
	}

}
