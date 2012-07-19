package com.adviser.harhar

/**
 * @author marwol
 */
class CliLogger implements Logger {

  boolean debug

  CliLogger(debug) {
    this.debug = debug
  }

  void debug(message) {
    if (debug) {
      println message
    }
  }

  void info(message) {
    println message
  }
}
