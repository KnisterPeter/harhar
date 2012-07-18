package com.adviser.harhar

import java.util.concurrent.CountDownLatch

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.adviser.harhar.result.EntryResult
import com.ning.http.client.AsyncHandler
import com.ning.http.client.HttpResponseBodyPart
import com.ning.http.client.HttpResponseHeaders
import com.ning.http.client.HttpResponseStatus
import com.ning.http.client.Response
import com.ning.http.client.AsyncHandler.STATE
import com.ning.http.client.Response.ResponseBuilder

class HttpCallback implements AsyncHandler<Response> {

  static final Logger LOGGER = LoggerFactory.getLogger(HttpCallback.class)

  ResponseBuilder responseBuilder = new ResponseBuilder()

  CountDownLatch cdl

  EntryResult result

  HttpCallback(cdl, result) {
    this.cdl = cdl
    this.result = result
  }

  void onThrowable(final Throwable t) {
    cdl.countDown()
    LOGGER.warn("Request failed", t)
  }

  STATE onStatusReceived(HttpResponseStatus responseStatus) {
    responseBuilder.reset()
    responseBuilder.accumulate(responseStatus)
    return STATE.CONTINUE
  }

  STATE onHeadersReceived(HttpResponseHeaders headers) {
    responseBuilder.accumulate(headers)
    return STATE.CONTINUE
  }

  STATE onBodyPartReceived(HttpResponseBodyPart bodyPart) {
    // TODO Auto-generated method stub
    return STATE.CONTINUE
  }

  Response onCompleted() {
    result.setEnd(System.currentTimeMillis())
    cdl.countDown()
    responseBuilder.build()
  }
}
