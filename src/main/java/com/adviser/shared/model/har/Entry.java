package com.adviser.shared.model.har;

import java.io.Serializable;
import java.util.Map;

public class Entry implements Serializable {

  private String pageref;

  private String startedDateTime;

  private int time;

  private Request request;

  private Response response;

  private Map<String, String> cache;

  private Timings timings;

  /**
   * @return the pageref
   */
  public final String getPageref() {
    return pageref;
  }

  /**
   * @param pageref
   *          the pageref to set
   */
  public final void setPageref(final String pageref) {
    this.pageref = pageref;
  }

  /**
   * @return the startedDateTime
   */
  public final String getStartedDateTime() {
    return startedDateTime;
  }

  /**
   * @param startedDateTime
   *          the startedDateTime to set
   */
  public final void setStartedDateTime(final String startedDateTime) {
    this.startedDateTime = startedDateTime;
  }

  /**
   * @return the time
   */
  public final int getTime() {
    return time;
  }

  /**
   * @param time
   *          the time to set
   */
  public final void setTime(final int time) {
    this.time = time;
  }

  /**
   * @return the request
   */
  public final Request getRequest() {
    return request;
  }

  /**
   * @param request
   *          the request to set
   */
  public final void setRequest(final Request request) {
    this.request = request;
  }

  /**
   * @return the response
   */
  public final Response getResponse() {
    return response;
  }

  /**
   * @param response
   *          the response to set
   */
  public final void setResponse(final Response response) {
    this.response = response;
  }

  /**
   * @return the cache
   */
  public final Map<String, String> getCache() {
    return cache;
  }

  /**
   * @param cache
   *          the cache to set
   */
  public final void setCache(final Map<String, String> cache) {
    this.cache = cache;
  }

  /**
   * @return the timings
   */
  public final Timings getTimings() {
    return timings;
  }

  /**
   * @param timings
   *          the timings to set
   */
  public final void setTimings(final Timings timings) {
    this.timings = timings;
  }

}
