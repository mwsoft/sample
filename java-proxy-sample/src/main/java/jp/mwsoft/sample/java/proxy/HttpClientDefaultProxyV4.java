package jp.mwsoft.sample.java.proxy;

import java.net.ProxySelector;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.ProxySelectorRoutePlanner;
import org.apache.http.util.EntityUtils;

public class HttpClientDefaultProxyV4 {

	public static void main(String[] args) throws Exception {

		// プロキシの情報を設定
		System.setProperty("proxySet", "true");
		System.setProperty("proxyHost", "192.168.1.33");
		System.setProperty("proxyPort", "8080");

		DefaultHttpClient client = new DefaultHttpClient();

		// デフォルトのProxySelectorを利用するよう設定
		ProxySelectorRoutePlanner routePlanner = new ProxySelectorRoutePlanner(client.getConnectionManager()
				.getSchemeRegistry(), ProxySelector.getDefault());
		client.setRoutePlanner(routePlanner);

		// リクエスト実行
		HttpResponse response1 = client.execute(new HttpGet("http://www.yahoo.co.jp/"));
		System.out.println(response1.getStatusLine().getStatusCode());

		// 接続閉じる
		EntityUtils.consume(response1.getEntity());

		// 結果を見分けやすいよう3秒待機
		Thread.sleep(3000);

		// setParameterで別のプロキシを指定してみる
		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost("192.168.1.36", 8080));

		// リクエスト実行
		HttpResponse response2 = client.execute(new HttpGet("http://www.google.co.jp/"));
		System.out.println(response2.getStatusLine().getStatusCode());

		/*
		 * 上記のコードを実行した際のパケットキャプチャ
		 * 
		 * ======================================================================
		 * 192.168.1.34 -> 192.168.1.33 HTTP GET http://www.yahoo.co.jp/
		 * HTTP/1.1192.168.1.33 -> 192.168.1.34 HTTP HTTP/1.1 200 OK (text/html)
		 * 192.168.1.34 -> 192.168.1.33 HTTP GET http://www.google.co.jp/
		 * HTTP/1.1192.168.1.33 -> 192.168.1.34 HTTP HTTP/1.1 200 OK (text/html)
		 * ==
		 * ====================================================================
		 * 192.168.1.33と192.168.1.36がプロキシ 192.168.1.34がプログラムを実行したマシン
		 * 
		 * setRoutePlannerで指定した後にsetParameterで新しいプロキシを私邸しても無視された
		 * 逆にsetParameter後にsetRoutePlannerで新しいプロキシを私邸した場合は、反映される
		 */
	}
}
