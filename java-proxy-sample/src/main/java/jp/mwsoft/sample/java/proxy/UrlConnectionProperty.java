package jp.mwsoft.sample.java.proxy;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class UrlConnectionProperty {

	public static void main(String[] args) throws Exception {

		// URLConnectionのopenConnection時に使用するプロキシを指定する
		URL url = new URL("http://www.yahoo.co.jp/");
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.1.33", 8080));
		HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
		System.out.println(conn.getResponseCode());

		/*
		 * 上記コードを実行した際のパケットキャプチャ
		 * 
		 * ======================================================================
		 * 192.168.1.34 -> 192.168.1.33 HTTP GET http://www.yahoo.co.jp/ HTTP/1.1
		 * ======================================================================
		 * 192.168.1.33がプロキシ、192.168.1.34がプログラムを実行したマシン
		 * 
		 * ちゃんとプロキシが利用されている
		 */
	}
}
