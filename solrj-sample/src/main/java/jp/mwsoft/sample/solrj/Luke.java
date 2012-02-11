package jp.mwsoft.sample.solrj;

import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.request.LukeRequest;
import org.apache.solr.common.util.NamedList;

public class Luke {

	public static void main(String[] args) throws Exception {

		// 初期化
		CommonsHttpSolrServer server = new CommonsHttpSolrServer("http://localhost:8983/solr");

		// リクエスト実行
		NamedList<Object> namedList = server.request(new LukeRequest());

		NamedList<Object> index = (NamedList<Object>) namedList.get("index");
		// ドキュメント数
		System.out.println(index.get("numDocs"));
		  //=> 5
		// セグメント数
		System.out.println(index.get("segmentCount"));
		  //=> 3
		// 最終更新日時
		System.out.println(index.get("lastModified"));
		  //=> Sat Feb 11 17:11:21 JST 2012

		// テキストフィールドの頻出語を取る
		NamedList<Object> fields = (NamedList<Object>) namedList.get("fields");
		NamedList<Object> text = (NamedList<Object>) fields.get("text");
		NamedList<Object> topTerms = (NamedList<Object>) text.get("topTerms");
		for (Object term : topTerms)
			System.out.println(term);
  		      //=> プル=3
	          //=> ンプ=3
		      //=> サン=3  以下略

	}
}
