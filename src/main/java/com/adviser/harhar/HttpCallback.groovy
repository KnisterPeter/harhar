package com.adviser.harhar

import java.util.concurrent.CountDownLatch

import com.adviser.harhar.result.EntryResult
import com.ning.http.client.AsyncHandler
import com.ning.http.client.HttpResponseBodyPart
import com.ning.http.client.HttpResponseHeaders
import com.ning.http.client.HttpResponseStatus
import com.ning.http.client.Response
import com.ning.http.client.AsyncHandler.STATE
import com.ning.http.client.Response.ResponseBuilder

class HttpCallback implements AsyncHandler<Response> {


  ResponseBuilder responseBuilder = new ResponseBuilder()

  CountDownLatch cdl

  EntryResult result

  HttpCallback(cdl, result) {
    this.cdl = cdl
    this.result = result
  }

  void onThrowable(final Throwable t) {
    cdl?.countDown()
    t.printStackTrace()
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
    bodyPart.writeTo(result.bcos)
    return STATE.CONTINUE
  }

  Response onCompleted() {
    result?.end = System.currentTimeMillis()
    cdl?.countDown()
    responseBuilder.build()
  }
}
