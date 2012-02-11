package jp.mwsoft.sample.solrj;

import java.io.File;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.SolrCore;
import org.apache.solr.search.SolrIndexSearcher;
import org.apache.solr.util.RefCounted;

public class LuceneSearch {

	public static void main(String[] args) throws Exception {
		// 初期化処理
		String solrXmlPath = "apache-solr-3.5.0/example/solr/solr.xml";
		String solrHome = "apache-solr-3.5.0/example/solr";
		CoreContainer container = new CoreContainer();
		container.load(solrHome, new File(solrXmlPath));

		// SolrCoreからSolrIndexSearcherを取得
		SolrCore core = container.getCore("coreName");
		RefCounted<SolrIndexSearcher> ref = core.getSearcher();
		SolrIndexSearcher searcher = ref.get();

		// SolrIndexSearcherはLuceneのIndexSearcherを継承してるので
		// Luceneっぽく利用できる
		Query query = new TermQuery(new Term("text", "文書"));
		TopDocs topDocs = searcher.search(query, 10);
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			System.out.println(searcher.doc(scoreDoc.doc));
		}

		// 終了処理
		// RefCountedからgetしたらdecrefする
		ref.decref();
		core.close();
		container.shutdown();
	}
}
