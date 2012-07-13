package com.adviser.shared.model.har;

import java.io.Serializable;

public class Har implements Serializable {

  private final Log log = new Log();

  public void addHar(final Har har) {
    log.addLog(har.getLog());
  }

  /**
   * @return the log
   */
  public final Log getLog() {
    return log;
  }

}
