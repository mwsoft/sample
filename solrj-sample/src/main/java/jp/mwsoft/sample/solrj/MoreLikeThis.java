package jp.mwsoft.sample.solrj;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.NamedList;

public class MoreLikeThis {

	public static void main(String[] args) throws Exception {

		// 初期化
		CommonsHttpSolrServer server = new CommonsHttpSolrServer("http://localhost:8983/solr");

		// ID=1の文書の類似文書を探すクエリを設定
		SolrQuery query = new SolrQuery("id:1");
		query.set("mlt", true);
		query.set("mlt.fl", "text");
		query.set("mlt.mindf", 1);
		query.set("mlt.mintf", 1);
		query.set("fl", "id,score,text");

		// 検索実行
		QueryResponse response = server.query(query);

		// MoreLikeThisの結果を取得
		NamedList<Object> moreLikeThis = (NamedList<Object>) response.getResponse().get("moreLikeThis");
		// 1つ目のドキュメントに対する類似文書の結果を取得（複数返る場合もある）
		List<SolrDocument> docs =  (List<SolrDocument>)moreLikeThis.getVal(0);
		// 結果を表示
		for (SolrDocument doc : docs)
			System.out.println(doc.get("score") + ", " + doc.get("text"));

		// 普通の検索結果には、id=1の文書の情報が入ってる
		SolrDocumentList list = response.getResults();
		System.out.println(list.getNumFound() + "件ヒットしました");
		for (SolrDocument doc : list)
			System.out.println(doc.get("id") + "," + doc.get("text"));

		// MoreLikeThisの各パラメータは以下を参照
		// http://wiki.apache.org/solr/MoreLikeThis
	}
}
