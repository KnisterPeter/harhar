package com.adviser.harhar.junit

import com.adviser.harhar.result.SimulatorResult

/**
 * @author marwol
 */
class HarAssert {

  static void assertMaxPageTime(SimulatorResult result, expected) {
    org.junit.Assert.assertTrue("Expected maximal page load time less than ${expected} ms but was ${result.maxUserTime} ms", result.maxUserTime <= expected)
  }
}
