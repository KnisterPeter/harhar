package com.adviser.shared.model;

import java.io.Serializable;

public class LoadValue implements Serializable {

  private static final long serialVersionUID = 6371112964221712893L;

  private volatile short value = 10; /* 0-100/100 => % */

  private volatile String url = null;

  private volatile int concurrent = 10;

  private Statistics statictics = new Statistics();

  /**
   * @return the value
   */
  public final short getValue() {
    return value;
  }

  /**
   * @param value
   *          the value to set
   */
  public final void setValue(final short value) {
    this.value = value;
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
   * @return the concurrent
   */
  public final int getConcurrent() {
    return concurrent;
  }

  /**
   * @param concurrent
   *          the concurrent to set
   */
  public final void setConcurrent(final int concurrent) {
    this.concurrent = concurrent;
  }

  /**
   * @return the statictics
   */
  public final Statistics getStatictics() {
    return statictics;
  }

  /**
   * @param statictics
   *          the statictics to set
   */
  public final void setStatictics(final Statistics statictics) {
    this.statictics = statictics;
  }

}
