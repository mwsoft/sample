package jp.mwsoft.sample.solrj;

import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.request.SolrPing;
import org.apache.solr.common.util.NamedList;

public class Ping {

	public static void main(String[] args) throws Exception {

		// 初期化
		CommonsHttpSolrServer server = new CommonsHttpSolrServer("http://localhost:8983/solr");

		// リクエスト実行
		NamedList<Object> namedList = server.request(new SolrPing());

		// ステータス確認
		System.out.println(namedList.get("status"));
		// => OK

		// reponseHeader要素の中を取得
		NamedList<Object> responseHeader = (NamedList<Object>) namedList.get("responseHeader");

		// 実行にかかった時間（ミリ秒）
		System.out.println(responseHeader.get("QTime"));
		// => 6

		// params要素の中を取得
		NamedList<Object> params = (NamedList<Object>) responseHeader.get("params");

		// paramsの中をすべて表示
		for (Object o : params) {
			System.out.println(o);
		}

		// 参考 : http://localhost:8983/solr/admin/ping
	}
}
