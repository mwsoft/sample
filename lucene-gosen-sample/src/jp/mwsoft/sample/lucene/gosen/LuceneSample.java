package jp.mwsoft.sample.lucene.gosen;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class LuceneSample {

	public static void main(String[] args) throws Exception {
		write();
		search();
	}

	public static void write() throws IOException {
		// GoSenが用意しているAnalyzer
		Analyzer analyzer = new JapaneseAnalyzer(Version.LUCENE_35);

		// インデックスディレクトリの設定
		Directory dir = new SimpleFSDirectory(new File("index"));
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, analyzer);
		IndexWriter writer = new IndexWriter(dir, config);

		// titleとcontentというフィールドを持つドキュメントをインデックスに追加してみる
		Document doc = new Document();
		doc.add(new Field("title", "将来の夢", Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("content", "生まれ変わってキャーのお腹で眠りたい", Field.Store.YES, Field.Index.ANALYZED));
		writer.addDocument(doc);

		// しゅーりょー
		writer.close();
	}

	public static void search() throws IOException, ParseException {
		// 検索の準備
		IndexReader reader = IndexReader.open(FSDirectory.open(new File("index")), true);
		IndexSearcher searcher = new IndexSearcher(reader);

		// 検索クエリの準備
		Analyzer analyzer = new JapaneseAnalyzer(Version.LUCENE_35);
		QueryParser parser = new QueryParser(Version.LUCENE_35, "content", analyzer);
		Query query = parser.parse("content:生まれ変わろう");

		// 検索の実行
		TopScoreDocCollector collector = TopScoreDocCollector.create(1000, false);
		searcher.search(query, collector);

		// 検索結果（上位10件）の出力
		ScoreDoc[] hits = collector.topDocs(0, 10).scoreDocs;
		for (ScoreDoc hit : hits) {
			Document doc = searcher.doc(hit.doc);
			System.out.println(doc.get("title") + " | " + doc.get("content"));
		}
	}
}
