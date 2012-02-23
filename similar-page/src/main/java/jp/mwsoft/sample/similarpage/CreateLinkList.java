package jp.mwsoft.sample.similarpage;

import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class CreateLinkList {

	// 対象となるパスを取得する
	public static Set<String> getPathList(String baseUrl) throws Exception {
		Queue<String> queue = new LinkedList<String>();
		queue.add(baseUrl);
		Set<String> urlSet = new HashSet<String>();

		while (queue.size() > 0) {
			String url = queue.poll();
			try {
				HtmlParser parser = new HtmlParser(url);
				if (parser.responseCode == 200) {
					for (String link : parser.links) {
						if (checkLink(url, link)) {
							URL newUrl = new URL(new URL(url), link);
							if (!urlSet.contains(newUrl.getPath())) {
								urlSet.add(newUrl.getPath());
								queue.add(newUrl.toString());
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return urlSet;
	}

	// ローカルリンク且つ、.htmlで終了しているものを対象とする
	private static boolean checkLink(String url, String link) {
		try {
			new URL(new URL(url), link);
		} catch (Exception e) {
			return false;
		}
		return !link.startsWith("http") && !link.contains("#");
	}
}
