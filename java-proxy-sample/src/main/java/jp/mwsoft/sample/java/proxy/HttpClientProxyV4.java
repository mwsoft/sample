package jp.mwsoft.sample.java.proxy;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpClientProxyV4 {

	public static void main(String[] args) throws Exception {

		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost("192.168.1.33", 8080));

		HttpResponse response = client.execute(new HttpGet("http://www.yahoo.co.jp/"));
		System.out.println(response.getStatusLine().getStatusCode());

		/*
		 * 上記のコードを実行した際のパケットキャプチャ
		 * 
		 * ======================================================================
		 * 4240.373418 192.168.1.34 -> 192.168.1.33 HTTP GET http://www.yahoo.co.jp/ HTTP/1.1 
		 * 4240.427230 192.168.1.33 -> 192.168.1.34 HTTP HTTP/1.1 200 OK  (text/html)
		 * ======================================================================
		 * 192.168.1.33がプロキシ、192.168.1.34がプログラムを実行したマシン
		 * 
		 * ちゃんとプロキシが使われている
		 */
	}
}
