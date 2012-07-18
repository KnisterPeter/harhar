package com.adviser.harhar;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adviser.harhar.result.EntryResult;
import com.ning.http.client.AsyncHandler;
import com.ning.http.client.HttpResponseBodyPart;
import com.ning.http.client.HttpResponseHeaders;
import com.ning.http.client.HttpResponseStatus;
import com.ning.http.client.Response;
import com.ning.http.client.Response.ResponseBuilder;

/**
 * @author marwol
 */
public class HttpCallback implements AsyncHandler<Response> {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpCallback.class);

  private final ResponseBuilder responseBuilder = new ResponseBuilder();

  private final CountDownLatch cdl;

  private final EntryResult result;

  /**
   * @param cdl
   * @param result
   */
  public HttpCallback(final CountDownLatch cdl, final EntryResult result) {
    this.cdl = cdl;
    this.result = result;
  }

  /**
   * @see com.ning.http.client.AsyncHandler#onThrowable(java.lang.Throwable)
   */
  @Override
  public void onThrowable(final Throwable t) {
    cdl.countDown();
    HttpCallback.LOGGER.warn("Request failed", t);
  }

  /**
   * @see com.ning.http.client.AsyncHandler#onStatusReceived(com.ning.http.client.HttpResponseStatus)
   */
  @Override
  public com.ning.http.client.AsyncHandler.STATE onStatusReceived(final HttpResponseStatus responseStatus) throws Exception {
    responseBuilder.reset();
    responseBuilder.accumulate(responseStatus);
    return STATE.CONTINUE;
  }

  /**
   * @see com.ning.http.client.AsyncHandler#onHeadersReceived(com.ning.http.client.HttpResponseHeaders)
   */
  @Override
  public com.ning.http.client.AsyncHandler.STATE onHeadersReceived(final HttpResponseHeaders headers) throws Exception {
    responseBuilder.accumulate(headers);
    return STATE.CONTINUE;
  }

  /**
   * @see com.ning.http.client.AsyncHandler#onBodyPartReceived(com.ning.http.client.HttpResponseBodyPart)
   */
  @Override
  public com.ning.http.client.AsyncHandler.STATE onBodyPartReceived(final HttpResponseBodyPart bodyPart) throws Exception {
    // TODO Auto-generated method stub
    return STATE.CONTINUE;
  }

  /**
   * @see com.ning.http.client.AsyncHandler#onCompleted()
   */
  @Override
  public Response onCompleted() throws Exception {
    result.setEnd(System.currentTimeMillis());
    cdl.countDown();
    return responseBuilder.build();
  }

}
