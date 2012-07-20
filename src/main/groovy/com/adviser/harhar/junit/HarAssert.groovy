package com.adviser.harhar.junit

import com.adviser.harhar.result.SimulatorResult

/**
 * @author marwol
 */
class HarAssert {

  static void assertMinPageTime(SimulatorResult result, expected) {
    org.junit.Assert.assertTrue("Expected minimal page load time less than ${expected} ms but was ${result.minUserTime} ms", result.minUserTime <= expected)
  }

  static void assertAvgPageTime(SimulatorResult result, expected) {
    org.junit.Assert.assertTrue("Expected average page load time less than ${expected} ms but was ${result.avgUserTime} ms", result.avgUserTime <= expected)
  }

  static void assertMaxPageTime(SimulatorResult result, expected) {
    org.junit.Assert.assertTrue("Expected maximal page load time less than ${expected} ms but was ${result.maxUserTime} ms", result.maxUserTime <= expected)
  }
}
