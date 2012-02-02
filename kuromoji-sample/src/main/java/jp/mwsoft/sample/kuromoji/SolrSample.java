package jp.mwsoft.sample.kuromoji;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class SolrSample {

	public static void main(String[] args) throws Exception {
		deleteAll();
		add("1", "タイトル", "コンピューターを買う");
		search("コンピュータ");
		search("買った");
	}

	public static void search(String content) throws Exception {
		CommonsHttpSolrServer server = new CommonsHttpSolrServer("http://localhost:8983/solr");
		SolrQuery query = new SolrQuery("description:" + content);
		SolrDocumentList docs = server.query(query).getResults();
		for (SolrDocument doc : docs) {
			System.out.println(doc);
		}
	}

	public static void add(String id, String title, String content) throws Exception {
		CommonsHttpSolrServer server = new CommonsHttpSolrServer("http://localhost:8983/solr");
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", id);
		doc.addField("title", title);
		doc.addField("description", content);
		server.add(doc);
		server.commit();
	}

	public static void deleteAll() throws Exception {
		CommonsHttpSolrServer server = new CommonsHttpSolrServer("http://localhost:8983/solr");
		server.deleteByQuery("*:*");
		server.commit();
	}
}
