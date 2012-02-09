package jp.mwsoft.sample;

import java.io.File;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.core.CoreContainer;

public class Update {

    public static void main(String[] args) throws Exception {
        // 初期化処理
        String solrXmlPath = "apache-solr-3.5.0/example/solr/solr.xml";
        String solrHome = "apache-solr-3.5.0/example/solr";
        CoreContainer container = new CoreContainer();
        container.load(solrHome, new File(solrXmlPath));
        EmbeddedSolrServer server = new EmbeddedSolrServer(container, "coreName");

        // 同一のIDの文書をaddすると、そのIDの文書が更新される
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", "1");
        doc.addField("text", "サンプル用の文書です（更新）");

        // 登録処理
        server.add(doc);

        // 登録した文書はcommitしないと反映されない
        server.commit();

        // 終了処理
        container.shutdown();
    }

}
