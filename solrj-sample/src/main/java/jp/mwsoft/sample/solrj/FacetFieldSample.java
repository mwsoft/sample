package jp.mwsoft.sample.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;

public class FacetFieldSample {

	public static void main(String[] args) throws Exception {

		// 初期化
		CommonsHttpSolrServer server = new CommonsHttpSolrServer("http://localhost:8983/solr");

		// 下記のようなクエリを実行する
		// http://localhost:8983/solr/select/?q=*:*&facet=true&facet.field=text

		// クエリを設定
		SolrQuery query = new SolrQuery("*:*");
		query.setFacet(true);
		query.addFacetField("text");

		// 検索実行
		QueryResponse response = server.query(query);

		// Facetの結果を取得
		for (FacetField field : response.getFacetFields()) {
			System.out.println("総数 : " + field.getValueCount());
			for (Count count : field.getValues())
				System.out.println(count.getName() + ", " + count.getCount());
		}
	}

}
