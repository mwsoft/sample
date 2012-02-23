package jp.mwsoft.sample.similarpage;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.util.NamedList;

public class CreateSimilarList {

	public static CommonsHttpSolrServer server;;

	public static String BASE_PATH = "workspace/www";

	public static void main(String[] args) throws Exception {
		server = new CommonsHttpSolrServer("http://localhost:8983/solr");
		for (String path : CreateLinkList.getPathList("http://localhost/")) {
			if (!path.endsWith(".html"))
				continue;
			try {
				System.out.println(path);
				outJson(path);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void outJson(String path) throws Exception {
		String json = getJson(path, 0.2f);
		if (json == null)
			return;

		File dir = new File(new File(BASE_PATH + path).getParent() + "/js/");
		dir.mkdir();
		String outPath = dir.getCanonicalPath() + "/" + new File(path).getName() + ".json";
		Writer writer = new FileWriter(outPath);
		writer.write(json);
		writer.close();
	}

	private static String getJson(String path, float minScore) throws Exception {
		SolrQuery query = new SolrQuery("path:" + path);
		query.set("mlt", true);
		query.set("mlt.fl", "content");
		query.set("mlt.mindf", 1);
		query.set("mlt.mintf", 1);
		query.set("mlt.maxntp", 10000);
		query.set("mlt.maxqt", 50);
		query.set("mlt.count", 10);
		query.set("fl", "score,title,path");

		// 検索実行
		QueryResponse response = server.query(query);

		// MoreLikeThisの結果を取得
		NamedList<Object> moreLikeThis = (NamedList<Object>) response.getResponse().get("moreLikeThis");

		// 1つ目のドキュメントに対する類似文書の結果を取得（検索条件によっては複数返る）
		if (moreLikeThis.size() == 0)
			return null;

		List<SolrDocument> docs = (List<SolrDocument>) moreLikeThis.getVal(0);

		// 結果を表示
		StringBuilder builder = new StringBuilder("[ ");
		for (SolrDocument doc : docs)
			builder.append(String.format("{ \"title\": \"%s\", \"link\": \"%s\", \"score\": %f }, ", doc.get("title")
					.toString().replaceAll("\\| mwSoft", "").trim(), doc.get("path"), doc.get("score")));
		builder.deleteCharAt(builder.length() - 2);
		builder.append(" ]");

		return builder.toString();
	}
}
