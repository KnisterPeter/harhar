package com.adviser.loadGenerator;

import org.apache.camel.component.http4.HttpClientConfigurer;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;
import org.springframework.stereotype.Component;

@Component("NoKeepAlive")
public class NoKeepAlive implements HttpClientConfigurer {
	public void configureHttpClient(HttpClient hc) {
		((AbstractHttpClient)hc).setReuseStrategy(new ConnectionReuseStrategy() {
			
			public boolean keepAlive(HttpResponse arg0, HttpContext arg1) {
				return false;
			}
		});
	}
}
