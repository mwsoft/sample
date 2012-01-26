package jp.mwsoft.sample.java.proxy;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpClientProxyV3 {

	public static void main(String[] args) throws Exception {
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setProxy("192.168.1.33", 8080);

		HttpMethod method = new GetMethod("http://www.yahoo.co.jp");
		client.executeMethod(method);
		System.out.println(method.getStatusCode());
		
		/*
		 * 上記のコードを実行した際のパケットキャプチャ
		 * 
		 * ======================================================================
		 * 192.168.1.34 -> 192.168.1.33 HTTP GET http://www.yahoo.co.jp/ HTTP/1.1 
         * 192.168.1.33 -> 192.168.1.34 HTTP HTTP/1.1 200 OK  (text/html)
		 * ======================================================================
		 * 192.168.1.33がプロキシ、192.168.1.34がプログラムを実行したマシン
		 * 
		 * ちゃんとプロキシが使われている
		 */
	}

}
