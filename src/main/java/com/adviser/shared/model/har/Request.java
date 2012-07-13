package com.adviser.shared.model.har;

import java.io.Serializable;
import java.util.List;

public class Request implements Serializable {

  private String method;

  private String url;

  private String httpVersion;

  private List<NameValue> headers;

  private List<NameValue> queryString;

  private List<Cookie> cookies;

  private int headersSize;

  private int bodySize;

  private PostData postData;

  /**
   * @return the method
   */
  public final String getMethod() {
    return method;
  }

  /**
   * @param method
   *          the method to set
   */
  public final void setMethod(final String method) {
    this.method = method;
  }

  /**
   * @return the url
   */
  public final String getUrl() {
    return url;
  }

  /**
   * @param url
   *          the url to set
   */
  public final void setUrl(final String url) {
    this.url = url;
  }

  /**
   * @return the httpVersion
   */
  public final String getHttpVersion() {
    return httpVersion;
  }

  /**
   * @param httpVersion
   *          the httpVersion to set
   */
  public final void setHttpVersion(final String httpVersion) {
    this.httpVersion = httpVersion;
  }

  /**
   * @return the headers
   */
  public final List<NameValue> getHeaders() {
    return headers;
  }

  /**
   * @param headers
   *          the headers to set
   */
  public final void setHeaders(final List<NameValue> headers) {
    this.headers = headers;
  }

  /**
   * @return the queryString
   */
  public final List<NameValue> getQueryString() {
    return queryString;
  }

  /**
   * @param queryString
   *          the queryString to set
   */
  public final void setQueryString(final List<NameValue> queryString) {
    this.queryString = queryString;
  }

  /**
   * @return the cookies
   */
  public final List<Cookie> getCookies() {
    return cookies;
  }

  /**
   * @param cookies
   *          the cookies to set
   */
  public final void setCookies(final List<Cookie> cookies) {
    this.cookies = cookies;
  }

  /**
   * @return the headersSize
   */
  public final int getHeadersSize() {
    return headersSize;
  }

  /**
   * @param headersSize
   *          the headersSize to set
   */
  public final void setHeadersSize(final int headersSize) {
    this.headersSize = headersSize;
  }

  /**
   * @return the bodySize
   */
  public final int getBodySize() {
    return bodySize;
  }

  /**
   * @param bodySize
   *          the bodySize to set
   */
  public final void setBodySize(final int bodySize) {
    this.bodySize = bodySize;
  }

  /**
   * @return the postData
   */
  public final PostData getPostData() {
    return postData;
  }

  /**
   * @param postData
   *          the postData to set
   */
  public final void setPostData(final PostData postData) {
    this.postData = postData;
  }

}
