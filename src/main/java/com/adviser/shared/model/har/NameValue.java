package com.adviser.shared.model.har;

import java.io.Serializable;

public class NameValue implements Serializable {

  private String name;

  private String value;

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

}
