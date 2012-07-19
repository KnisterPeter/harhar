package com.adviser.harhar.junit

import com.adviser.harhar.Logger

/**
 * @author marwol
 */
class JUnitLogger implements Logger {

  boolean debug

  JUnitLogger(debug) {
    this.debug = debug
  }

  void debug(message) {
    if (debug) {
      info(message)
    }
  }

  void info(message) {
    println message
  }
}
