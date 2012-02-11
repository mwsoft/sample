package jp.mwsoft.sample.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class Search {
	public static void main(String[] args) throws Exception {

		// 初期化
		CommonsHttpSolrServer server = new CommonsHttpSolrServer("http://localhost:8983/solr");

		// 検索
		SolrQuery query = new SolrQuery("text:文書");
		query.setFields("id", "text");
		QueryResponse response = server.query(query);

		// 検索結果の出力
		SolrDocumentList list = response.getResults();
		System.out.println(list.getNumFound() + "件ヒットしました");
		for (SolrDocument doc : list)
			System.out.println(doc.get("id") + "," + doc.get("text"));
	}

}
