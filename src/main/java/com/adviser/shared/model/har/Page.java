package com.adviser.shared.model.har;

import java.io.Serializable;

public class Page implements Serializable {

  private String startedDateTime;

  private String id;

  private String title;

  private PageTimings pageTimings;

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
   * @return the id
   */
  public final String getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public final void setId(final String id) {
    this.id = id;
  }

  /**
   * @return the title
   */
  public final String getTitle() {
    return title;
  }

  /**
   * @param title
   *          the title to set
   */
  public final void setTitle(final String title) {
    this.title = title;
  }

  /**
   * @return the pageTimings
   */
  public final PageTimings getPageTimings() {
    return pageTimings;
  }

  /**
   * @param pageTimings
   *          the pageTimings to set
   */
  public final void setPageTimings(final PageTimings pageTimings) {
    this.pageTimings = pageTimings;
  }

}
