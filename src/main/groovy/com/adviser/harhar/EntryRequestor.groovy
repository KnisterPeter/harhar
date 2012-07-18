package com.adviser.harhar

import java.util.concurrent.Callable
import java.util.concurrent.CountDownLatch

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.adviser.harhar.model.Entry
import com.adviser.harhar.result.EntryResult
import com.ning.http.client.AsyncHttpClient
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder


/**
 * @author marwol
 */
class EntryRequestor implements Callable<EntryResult> {

  Logger LOGGER = LoggerFactory.getLogger(EntryRequestor.class)

  def baseUrl

  def baseUrlSsl

  Entry entry

  CountDownLatch cdl

  AsyncHttpClient client

  EntryRequestor(baseUrl, Entry entry, CountDownLatch cdl, AsyncHttpClient client) {
    this.baseUrl = baseUrl
    if (baseUrl) {
      this.baseUrlSsl = baseUrl.replaceFirst("http", "https")
    }
    this.entry = entry
    this.cdl = cdl
    this.client = client
  }

  EntryResult call() {
    try {
      BoundRequestBuilder request = createRequest(client, entry)
      EntryResult result = new EntryResult(entry)
      result.setResponse(request.execute(new HttpCallback(cdl, result)))
      return result
    } catch (all) {
      cdl.countDown()
      LOGGER.error("Failed to execute request: " + entry.getRequest().getUrl(), all)
    }
  }

  BoundRequestBuilder createRequest(AsyncHttpClient client, Entry entry) {
    def url = entry.request.url
    if (baseUrl) {
      url = url
          .replaceFirst("http://[^/]+/", baseUrl)
          .replaceFirst("https://[^/]+/", baseUrlSsl)
    }
    LOGGER.info("Start request: {}", url)
    BoundRequestBuilder request
    switch (entry.request.method.toLowerCase()) {
      case "get":
        request = client.prepareGet(url)
        entry.request.queryString.each {
          request.addParameter(it.name, it.value)
        }
        break
      case "post":
        request = client.preparePost(url)
        request.setBody(entry.request.postData.text)
        break
    }
    entry.request.headers.each { request.addHeader(it.name, it.value) }
    return request
  }
}
