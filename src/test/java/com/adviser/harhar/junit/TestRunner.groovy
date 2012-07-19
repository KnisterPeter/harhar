package com.adviser.harhar.junit;

import org.junit.runner.RunWith

import com.adviser.harhar.result.SimulatorResult

/**
 * @author marwol
 */
@RunWith(HarHarRunner.class)
class TestRunner {

  @Har(value = "classpath:test.har", users = 10, repetitions = 10)
  void testHar(final SimulatorResult result) {
    HarAssert.assertMaxPageTime(result, 1000)
  }
}
