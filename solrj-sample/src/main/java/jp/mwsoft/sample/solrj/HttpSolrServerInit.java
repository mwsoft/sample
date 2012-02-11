package jp.mwsoft.sample.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;

public class HttpSolrServerInit {

	public static void main(String[] args) throws Exception {

        // localhostのSolrに繋ぐ
		CommonsHttpSolrServer server = new CommonsHttpSolrServer("http://localhost:8983/solr");
		
		// 10秒でタイムアウトに設定
		server.setConnectionTimeout( 10000 );
		
		// 3回までリトライ
		server.setMaxRetries( 3 );

		// 検索してみる
        System.out.println(server.query( new SolrQuery("*:*") ));
    }
}
