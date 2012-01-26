package jp.mwsoft.sample.java.proxy;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ProxySelectorWithUrlConnection {

	public static void main(String[] args) throws Exception {

		ProxySelector.setDefault(new MyProxySelector());

		// Yahooに繋ぐ通信は、192.168.1.33:8080が利用される
		URL url1 = new URL("http://www.yahoo.co.jp/");
		HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
		System.out.println(conn1.getResponseCode());

		Thread.sleep(3000);

		// Googleに繋ぐ通信は、192.168.1.36:8080が利用される
		URL url2 = new URL("http://www.google.co.jp/");
		HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
		System.out.println(conn2.getResponseCode());

		Thread.sleep(3000);

		// その他に繋ぐ通信は、192.168.1.36:8080が利用される
		URL url3 = new URL("http://www.twitter.com/");
		HttpURLConnection conn3 = (HttpURLConnection) url3.openConnection();
		System.out.println(conn3.getResponseCode());

		/*
		 * 上記コードを実行した際のパケットキャプチャ
		 * 
		 * ======================================================================
		 * 192.168.1.34 -> 192.168.1.33 HTTP GET http://www.yahoo.co.jp/ HTTP/1.1
		 * 192.168.1.33 -> 192.168.1.34 HTTP HTTP/1.1 200 OK (text/html)
		 * 
		 * 192.168.1.34 -> 192.168.1.36 HTTP GET http://www.google.co.jp/ HTTP/1.1 
		 * 192.168.1.36 -> 192.168.1.34 HTTP HTTP/1.1 200 OK (text/html)
		 * 
		 * 192.168.1.34 -> 199.59.148.10 HTTP GET / HTTP/1.1
		 * 199.59.148.10 -> 192.168.1.34 HTTP HTTP/1.1 301 Moved Permanently (text/html)
		 * 192.168.1.34 -> 199.59.149.198 HTTP GET / HTTP/1.1
		 * ======================================================================
		 * 192.168.1.33, 36がプロキシ、192.168.1.34がプログラムを実行したマシン
		 * 
		 * 1つ目（Yahoo）は33に、2つ目（Google）は36に、3つ目は直接接続に行っている
		 */
	}

}

class MyProxySelector extends ProxySelector {
	// プロキシを選択するメソッド
	public List<Proxy> select(URI uri) {
		String proxyHost = null;
		if ("www.yahoo.co.jp".equals(uri.getHost()))
			proxyHost = "192.168.1.33";
		else if ("www.google.co.jp".equals(uri.getHost()))
			proxyHost = "192.168.1.36";

		if (proxyHost != null)
			return Arrays.asList(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, 8080)));
		else
			return Arrays.asList(Proxy.NO_PROXY);
	}

	// Proxyへの接続に失敗すると呼ばれるメソッド
	public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
		ioe.printStackTrace();
	}
}
