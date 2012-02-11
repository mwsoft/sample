package jp.mwsoft.sample.solrj;

import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;

public class Delete {

	public static void main(String[] args) throws Exception {

		// 初期化
		CommonsHttpSolrServer server = new CommonsHttpSolrServer("http://localhost:8983/solr");

		// IDで削除
		server.deleteById("1");

		// Queryで削除
		server.deleteByQuery("text:ぶんしょ");

		// Commitしないと反映されない
		server.commit();
	}
}
