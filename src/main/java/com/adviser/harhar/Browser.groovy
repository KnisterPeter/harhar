package com.adviser.harhar

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

import org.apache.commons.lang3.concurrent.BasicThreadFactory

import com.adviser.harhar.model.Entry
import com.adviser.harhar.model.Page
import com.adviser.harhar.result.EntryResult
import com.adviser.harhar.result.PageResult
import com.ning.http.client.AsyncHttpClient
import com.ning.http.client.AsyncHttpClientConfig
import com.ning.http.client.providers.grizzly.GrizzlyAsyncHttpProvider

/**
 * @author marwol
 */
class Browser {

  def baseUrl

  AsyncHttpClient client

  ThreadPoolExecutor threadPool

  CountDownLatch cdl

  Browser(baseUrl) {
    this.baseUrl = baseUrl
    AsyncHttpClientConfig config = new AsyncHttpClientConfig.Builder()
        .setAllowPoolingConnection(true)
        .setMaxRequestRetry(5)
        .setRequestTimeoutInMs(1000 * 60)
        .setMaximumConnectionsPerHost(16)
        .setMaximumConnectionsTotal(10000)
        .build();
    client = new AsyncHttpClient(new GrizzlyAsyncHttpProvider(config));
    threadPool = Executors.newFixedThreadPool(8, new BasicThreadFactory.Builder().namingPattern(Thread.currentThread().getName() + "-browser-%d").daemon(true).build())
  }

  PageResult request(Page page, List<Entry> entries) {
    //println "Started page request: ${page.title} (${entries.size()})"
    cdl = new CountDownLatch(entries.size())
    PageResult result = new PageResult(page)
    List<Future<EntryResult>> list = entries.collect { threadPool.submit(new EntryRequestor(baseUrl, it, cdl, client)) }
    while (cdl.count > 0) {
      //println "Browser CDL ${cdl.count} for page ${page.title}"
      cdl.await(5, TimeUnit.SECONDS)
    }
    result.setEnd(System.currentTimeMillis())
    //println "Completed page request: ${page.title}"
    result.entryResults = list.collect { it.get() }
    result.entryResults.each { it.calculate() }
    return result
  }

  void quit() {
    threadPool.shutdown()
    client.close()
  }
}
