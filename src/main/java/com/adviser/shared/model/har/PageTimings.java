package com.adviser.shared.model.har;

import java.io.Serializable;

public class PageTimings implements Serializable {

  private int onContentLoad;

  private int onLoad;

  /**
   * @return the onContentLoad
   */
  public final int getOnContentLoad() {
    return onContentLoad;
  }

  /**
   * @param onContentLoad
   *          the onContentLoad to set
   */
  public final void setOnContentLoad(final int onContentLoad) {
    this.onContentLoad = onContentLoad;
  }

  /**
   * @return the onLoad
   */
  public final int getOnLoad() {
    return onLoad;
  }

  /**
   * @param onLoad
   *          the onLoad to set
   */
  public final void setOnLoad(final int onLoad) {
    this.onLoad = onLoad;
  }

}
