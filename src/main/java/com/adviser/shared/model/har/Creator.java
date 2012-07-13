package com.adviser.shared.model.har;

import java.io.Serializable;

public class Creator implements Serializable {

  private String name;

  private String version;

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
   * @return the version
   */
  public final String getVersion() {
    return version;
  }

  /**
   * @param version
   *          the version to set
   */
  public final void setVersion(final String version) {
    this.version = version;
  }

}
