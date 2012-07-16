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

  @Override
  public void configureHttpClient(final HttpClient hc) {
    ((AbstractHttpClient) hc).setReuseStrategy(new ConnectionReuseStrategy() {
      @Override
      public boolean keepAlive(final HttpResponse arg0, final HttpContext arg1) {
        return false;
      }
    });
  }

}
