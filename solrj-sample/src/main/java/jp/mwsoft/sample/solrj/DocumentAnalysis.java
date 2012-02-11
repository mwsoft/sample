package jp.mwsoft.sample.solrj;

import java.util.List;

import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.request.DocumentAnalysisRequest;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;

public class DocumentAnalysis {

	public static void main(String[] args) throws Exception {

		// 初期化
		CommonsHttpSolrServer server = new CommonsHttpSolrServer("http://localhost:8983/solr");

		// 解析するDocument
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "7");
		doc.addField("text", "解析用のドキュメントの本文です");

		// リクエスト実行
		DocumentAnalysisRequest request = new DocumentAnalysisRequest();
		request.addDocument(doc);
		NamedList<Object> namedList = server.request(request);

		// テキストがどう登録されるか確認する
		NamedList<Object> analysis = (NamedList<Object>) namedList.get("analysis");
		NamedList text = (NamedList) ((NamedList) analysis.getVal(0)).get("text");
		List list = (List) ((NamedList) ((NamedList) text.getVal(0)).getVal(0)).getVal(0);

		for (Object item : list)
			System.out.println(item);
	}
}
