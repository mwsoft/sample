package jp.mwsoft.sample.solrj;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

public class Add {

	public static void main(String[] args) throws Exception {

		// 初期化
		CommonsHttpSolrServer server = new CommonsHttpSolrServer("http://localhost:8983/solr");

		// 登録用のSolrInputDocumentを作る
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "1");
		doc.addField("text", "サンプル用の文書です");
		doc.addField("date", new Date()); // 日付はDateで入れられる

		// 登録処理
		server.add(doc);

		// List<SolrInputDocument>でまとめて登録もできる
		List<SolrInputDocument> list = new ArrayList<SolrInputDocument>();

		SolrInputDocument doc2 = new SolrInputDocument();
		doc2.addField("id", "2");
		doc2.addField("text", "サンプル文書その２");

		SolrInputDocument doc3 = new SolrInputDocument();
		doc3.addField("id", "3");
		doc3.addField("text", "サンプルぶんしょその３");
		doc3.addField("date", "2012-02-01T00:00:00Z"); // 日付は文字列でも入れられる

		list.add(doc2);
		list.add(doc3);

		// 登録処理
		server.add(list);

		// 登録した文書はcommitしないと反映されない
		server.commit();
		
		// commitする前ならrollback可
		server.rollback();
	}
}
