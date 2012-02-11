package jp.mwsoft.sample.solrj;

import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

public class Update {

	public static void main(String[] args) throws Exception {

		// 初期化
		CommonsHttpSolrServer server = new CommonsHttpSolrServer("http://localhost:8983/solr");

		// 同一のIDの文書をaddすると、そのIDの文書が更新される
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "1");
		doc.addField("text", "サンプル用の文書です（更新）");

		// 更新処理
		server.add(doc);

		// 登録した文書はcommitしないと反映されない
		server.commit();
	}

}
