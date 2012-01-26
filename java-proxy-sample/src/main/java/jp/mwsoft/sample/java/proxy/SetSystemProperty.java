package jp.mwsoft.sample.java.proxy;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class SetSystemProperty {

	public static void main(String[] args) throws Exception {

		// System.setPropertyで設定
		System.setProperty("proxySet", "true");
		System.setProperty("proxyHost", "192.168.1.33");
		System.setProperty("proxyPort", "8080");

		// URLConnectionで通信した場合は、上記のプロキシが適用される
		URL url = new URL("http://www.yahoo.co.jp/");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		int responseCode = conn.getResponseCode();
		System.out.println(responseCode);

		// 2つのリクエストを見分けやすいようにしばし待つ
		Thread.sleep(3000);

		// HTTPClientを用いた場合は、適用されない
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(new HttpGet("http://www.google.co.jp"));
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);

		/*
		 * 上記コードを実行した際のパケットキャプチャ
		 * 
		 * ======================================================================
		 * 192.168.1.34 -> 192.168.1.33 HTTP GET http://www.yahoo.co.jp/ HTTP/1.1
		 * 192.168.1.33 -> 192.168.1.34 HTTP HTTP/1.1 200 OK (text/html)
		 * 192.168.1.34 -> 74.125.235.88 HTTP GET / HTTP/1.1
		 * 74.125.235.88 -> 192.168.1.34 HTTP HTTP/1.1 200 OK (text/html)
		 * ======================================================================
		 * 192.168.1.33がプロキシ、192.168.1.34がプログラムを実行したマシン
		 * 
		 * 1回目（URLConnection）は192.168.1.33（適当に立てたプロキシサーバ）が利用され、
		 * 2回目（HttpClient）は74.125.235.88（google.co.jpのIP）に直接リクエストしている
		 */
	}

}
