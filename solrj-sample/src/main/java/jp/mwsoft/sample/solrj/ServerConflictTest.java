package jp.mwsoft.sample.solrj;

import java.io.File;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.core.CoreContainer;

public class ServerConflictTest {

	public static void main(String[] args) throws Exception {

		// HttpSolrServer
		CommonsHttpSolrServer httpServer = new CommonsHttpSolrServer("http://localhost:8983/solr");

		// EmbeddedSolrServer
		String solrXmlPath = "apache-solr-3.5.0/example/solr/solr.xml";
		String solrHome = "apache-solr-3.5.0/example/solr";
		CoreContainer container = new CoreContainer();
		container.load(solrHome, new File(solrXmlPath));
		EmbeddedSolrServer embeddedServer = new EmbeddedSolrServer(container, "coreName");

		// EmbeddedSolrServerで登録
		SolrInputDocument doc2 = new SolrInputDocument();
		doc2.addField("id", "6");
		doc2.addField("text", "Embeddeサーバから登録した（更新1）");
		embeddedServer.add(doc2);
		embeddedServer.commit();

		// HttpSolrServerで登録
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("id", "5");
		doc1.addField("text", "HTTPサーバから登録した（更新1）");
		httpServer.add(doc1);
		httpServer.commit();

		container.shutdown();
	}

}
