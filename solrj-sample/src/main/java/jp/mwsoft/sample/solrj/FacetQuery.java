package jp.mwsoft.sample.solrj;

import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;

public class FacetQuery {
	public static void main(String[] args) throws Exception {

		// 初期化
		CommonsHttpSolrServer server = new CommonsHttpSolrServer("http://localhost:8983/solr");

		// 下記のようなクエリを実行する
		// http://localhost:8983/solr/select/?q=*:*&facet=true&facet.query=date:[2012-01-01T00:00:00Z%20TO%202012-01-31T23:59:59Z]&facet.query=date:[2012-02-01T00:00:00Z%20TO%202012-02-28T23:59:59Z]

		// クエリを設定
		SolrQuery query = new SolrQuery("*:*");
		query.setFacet(true);
		query.addFacetQuery("date:[2012-01-01T00:00:00Z TO 2012-01-31T23:59:59Z]");
		query.addFacetQuery("date:[2012-02-01T00:00:00Z TO 2012-02-28T23:59:59Z]");

		// 検索実行
		QueryResponse response = server.query(query);

		// Facetの結果を取得
		for (Map.Entry<String, Integer> item : response.getFacetQuery().entrySet())
			System.out.println(item);
	}

}
