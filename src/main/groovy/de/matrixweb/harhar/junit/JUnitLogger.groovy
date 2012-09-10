package de.matrixweb.harhar.junit

import de.matrixweb.harhar.Logger

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
