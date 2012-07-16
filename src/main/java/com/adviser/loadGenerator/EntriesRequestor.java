package com.adviser.loadGenerator;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adviser.shared.model.har.Entry;
import com.adviser.shared.model.har.NameValue;
import com.adviser.shared.model.har.Request;

public class EntriesRequestor {

  private static final Logger LOGGER = LoggerFactory.getLogger(EntriesRequestor.class);

  final static int BrowserRequestLimit = 8;

  final static int BROWSERS = 20;

  final ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(EntriesRequestor.BROWSERS * EntriesRequestor.BrowserRequestLimit);
  ThreadPoolExecutor threadPool = new ThreadPoolExecutor(EntriesRequestor.BrowserRequestLimit,
      EntriesRequestor.BROWSERS * EntriesRequestor.BrowserRequestLimit, 10, TimeUnit.SECONDS, queue);

  public EntriesRequestor() {
    SchemeRegistry schemeRegistry = new SchemeRegistry();
    schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
    schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

    ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(schemeRegistry);
    // Increase max total connection to 200
    cm.setMaxTotal(EntriesRequestor.BrowserRequestLimit * EntriesRequestor.BROWSERS * 2);
    // Increase default max connection per route to 20
    cm.setDefaultMaxPerRoute(EntriesRequestor.BrowserRequestLimit * EntriesRequestor.BROWSERS * 2);
  }

  private class ProcessEntries {

    final List<Entry> entries;

    final AtomicInteger processed = new AtomicInteger();

    private class ProcessEntry implements Runnable {

      private HttpRequestBase method(final Request request) throws UnsupportedEncodingException {
        if (request.getMethod().equals("GET")) {
          return new HttpGet(request.getUrl());
        }
        if (request.getMethod().equals("POST")) {
          final HttpPost post = new HttpPost(request.getUrl());
          if (request.getPostData() != null) {
            post.setEntity(new StringEntity(request.getPostData().getText()));
          }
          // post.setEntity(request.getPostData().PO);
          // LOGGER.warn("method post is not implemented: {}",
          // request.getUrl());
          return post;
        }
        EntriesRequestor.LOGGER.warn("method cannot work with method:{}", request.getMethod());
        return null;
      }

      @Override
      public void run() {
        int requests = 0;
        int bytes = 0;
        final HttpClient httpclient = new DefaultHttpClient();
        while (true) {
          final int entryIdx = processed.addAndGet(1);
          if (entryIdx >= entries.size()) {
            EntriesRequestor.LOGGER.info("Done: {} {} {}", Thread.currentThread().getId(), "" + requests + ":" + bytes);
            break;
          }
          final Entry entry = entries.get(entryIdx);
          try {
            if (!new URL(entry.getRequest().getUrl()).getHost().endsWith("sinnerschrader.it")) {
              continue;
            }
            final HttpRequestBase request = this.method(entry.getRequest());
            final HttpParams params = new BasicHttpParams();

            for (NameValue nv : entry.getRequest().getQueryString()) {
              params.setParameter(nv.getName(), nv.getValue());
            }
            request.setParams(params);
            // entry.getRequest().
            for (NameValue nv : entry.getRequest().getHeaders()) {
              if (nv.getName().equalsIgnoreCase("Content-Length") && request.getMethod().equals("POST")) {
                continue;
              }
              if (nv.getName().equalsIgnoreCase("Cookie")) {
                continue;
              }
              request.addHeader(nv.getName(), nv.getValue());
            }
            final HttpResponse response = httpclient.execute(request);
            if (response.getStatusLine().getStatusCode() != 200) {
              EntriesRequestor.LOGGER.error("httpResponse:Status: {} {}", response.getStatusLine().getStatusCode(), entry.getRequest().getUrl());
            }
            // LOGGER.info("Cookie:{}", response.getHeaders("Cookie"));
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
              InputStream instream = entity.getContent();
              int l;
              byte[] tmp = new byte[4096];
              while ((l = instream.read(tmp)) != -1) {
                bytes += l;
              }
            }

          } catch (UnknownHostException e) {
            EntriesRequestor.LOGGER.info("httpRequest:UNKNOWNHost:" + entry.getRequest().getUrl());
          } catch (Throwable e) {
            EntriesRequestor.LOGGER.error("httpRequest:" + entry.getRequest().getUrl(), e);
          }

          ++requests;

          // LOGGER.info("Process: {} {}", Thread.currentThread().getId(),
          // entry.getRequest().getUrl());
        }
      }

    }

    public ProcessEntries(final List<Entry> entries) {
      this.entries = entries;
      processed.set(0);
      for (int i = 0; i < EntriesRequestor.BrowserRequestLimit; ++i) {
        threadPool.execute(new ProcessEntry());
      }
    }
  }

  public void request(final List<Entry> entries) {
    new ProcessEntries(entries);
  }

}
