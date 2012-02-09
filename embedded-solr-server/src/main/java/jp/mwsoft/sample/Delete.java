package jp.mwsoft.sample;

import java.io.File;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;

public class Delete {
    public static void main(String[] args) throws Exception {
        // 初期化処理
        String solrXmlPath = "apache-solr-3.5.0/example/solr/solr.xml";
        String solrHome = "apache-solr-3.5.0/example/solr";
        CoreContainer container = new CoreContainer();
        container.load(solrHome, new File(solrXmlPath));
        EmbeddedSolrServer server = new EmbeddedSolrServer(container, "coreName");

        // IDで削除
        server.deleteById("1");

        // Queryで削除
        server.deleteByQuery("text:ぶんしょ");

        // Commitしないと反映されない
        server.commit();

        // 終了処理
        container.shutdown();
    }
}
