package com.adviser.shared.model.har;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

  private int status;

  private String statusText;

  private String httpVersion;

  private List<NameValue> headers;

  private List<Cookie> cookies;

  private Content content;

  private String redirectURL;

  private int headersSize;

  private int bodySize;

  /**
   * @return the status
   */
  public final int getStatus() {
    return status;
  }

  /**
   * @param status
   *          the status to set
   */
  public final void setStatus(final int status) {
    this.status = status;
  }

  /**
   * @return the statusText
   */
  public final String getStatusText() {
    return statusText;
  }

  /**
   * @param statusText
   *          the statusText to set
   */
  public final void setStatusText(final String statusText) {
    this.statusText = statusText;
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
   * @return the content
   */
  public final Content getContent() {
    return content;
  }

  /**
   * @param content
   *          the content to set
   */
  public final void setContent(final Content content) {
    this.content = content;
  }

  /**
   * @return the redirectURL
   */
  public final String getRedirectURL() {
    return redirectURL;
  }

  /**
   * @param redirectURL
   *          the redirectURL to set
   */
  public final void setRedirectURL(final String redirectURL) {
    this.redirectURL = redirectURL;
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

}
