package jp.mwsoft.sample;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.core.CoreContainer;

public class Add {

    public static void main(String[] args) throws Exception {
        // 初期化処理
        String solrXmlPath = "apache-solr-3.5.0/example/solr/solr.xml";
        String solrHome = "apache-solr-3.5.0/example/solr";
        CoreContainer container = new CoreContainer();
        container.load(solrHome, new File(solrXmlPath));
        EmbeddedSolrServer server = new EmbeddedSolrServer(container, "coreName");

        // 登録用のSolrInputDocumentを作る
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", "1");
        doc.addField("text", "サンプル用の文書です");

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

        list.add(doc2);
        list.add(doc3);

        // 登録処理
        server.add(list);

        // 登録した文書はcommitしないと反映されない
        server.commit();

        // 終了処理
        container.shutdown();
    }
}
