package com.adviser.shared.model.har;

import java.io.Serializable;

public class Content implements Serializable {

  private int size;

  private int compression;

  private String mimeType;

  /**
   * @return the size
   */
  public final int getSize() {
    return size;
  }

  /**
   * @param size
   *          the size to set
   */
  public final void setSize(final int size) {
    this.size = size;
  }

  /**
   * @return the compression
   */
  public final int getCompression() {
    return compression;
  }

  /**
   * @param compression
   *          the compression to set
   */
  public final void setCompression(final int compression) {
    this.compression = compression;
  }

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

}
