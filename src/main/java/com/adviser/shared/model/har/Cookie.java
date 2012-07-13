package com.adviser.shared.model.har;

import java.io.Serializable;

public class Cookie implements Serializable {

  private String name;

  private String value;

  private String expires;

  private boolean httpOnly;

  private boolean secure;

  /**
   * @return the name
   */
  public final String getName() {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public final void setName(final String name) {
    this.name = name;
  }

  /**
   * @return the value
   */
  public final String getValue() {
    return value;
  }

  /**
   * @param value
   *          the value to set
   */
  public final void setValue(final String value) {
    this.value = value;
  }

  /**
   * @return the expires
   */
  public final String getExpires() {
    return expires;
  }

  /**
   * @param expires
   *          the expires to set
   */
  public final void setExpires(final String expires) {
    this.expires = expires;
  }

  /**
   * @return the httpOnly
   */
  public final boolean isHttpOnly() {
    return httpOnly;
  }

  /**
   * @param httpOnly
   *          the httpOnly to set
   */
  public final void setHttpOnly(final boolean httpOnly) {
    this.httpOnly = httpOnly;
  }

  /**
   * @return the secure
   */
  public final boolean isSecure() {
    return secure;
  }

  /**
   * @param secure
   *          the secure to set
   */
  public final void setSecure(final boolean secure) {
    this.secure = secure;
  }

}
