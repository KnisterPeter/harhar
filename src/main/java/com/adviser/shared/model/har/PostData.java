package com.adviser.shared.model.har;

import java.io.Serializable;
import java.util.List;

public class PostData implements Serializable {

  private String mimeType;

  private String text;

  private List<NameValue> params;

  /**
   * @return the mimeType
   */
  public final String getMimeType() {
    return mimeType;
  }

  /**
   * @param mimeType
   *          the mimeType to set
   */
  public final void setMimeType(final String mimeType) {
    this.mimeType = mimeType;
  }

  /**
   * @return the text
   */
  public final String getText() {
    return text;
  }

  /**
   * @param text
   *          the text to set
   */
  public final void setText(final String text) {
    this.text = text;
  }

  /**
   * @return the params
   */
  public final List<NameValue> getParams() {
    return params;
  }

  /**
   * @param params
   *          the params to set
   */
  public final void setParams(final List<NameValue> params) {
    this.params = params;
  }

}
