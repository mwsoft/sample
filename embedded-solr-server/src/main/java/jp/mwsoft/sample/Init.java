package jp.mwsoft.sample;

import java.io.File;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;

public class Init {

    public static void main(String[] args) throws Exception {
        // solr.xmlのパス
        String solrXmlPath = "apache-solr-3.5.0/example/solr/solr.xml";
        // SolrのHomeのパス
        String solrHome = "apache-solr-3.5.0/example/solr";

        // CoreContainerを初期化
        CoreContainer container = new CoreContainer();
        container.load(solrHome, new File(solrXmlPath));

        // solr.xmlに書いてあるコアの名前を指定してサーバを立ち上げる
        EmbeddedSolrServer server = new EmbeddedSolrServer(container, "coreName");

        // 終わる時はCoreContainerをshutdown
        // これをしないとプログラムが終わらない
        container.shutdown();
    }
}
