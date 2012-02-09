package jp.mwsoft.sample;

import java.io.File;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.core.CoreContainer;

public class Search {
    public static void main(String[] args) throws Exception {
        // 初期化処理
        String solrXmlPath = "apache-solr-3.5.0/example/solr/solr.xml";
        String solrHome = "apache-solr-3.5.0/example/solr";
        CoreContainer container = new CoreContainer();
        container.load(solrHome, new File(solrXmlPath));
        EmbeddedSolrServer server = new EmbeddedSolrServer(container, "coreName");

        // 検索
        SolrQuery query = new SolrQuery("text:文書");
        QueryResponse response = server.query(query);

        // 検索結果の出力
        SolrDocumentList list = response.getResults();
        System.out.println(list.getNumFound() + "件ヒットしました");
        for (SolrDocument doc : list) {
            System.out.println(doc.get("id") + "," + doc.get("text"));
        }

        // 終了処理
        container.shutdown();
    }

}
