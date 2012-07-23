package com.adviser.harhar.junit;

import org.junit.runner.RunWith

import com.adviser.harhar.result.SimulatorResult

/**
 * @author marwol
 */
@RunWith(HarHarRunner.class)
class TestRunner {

  @Har(value = "classpath:image.har", users = 10, repetitions = 10)
  void testHar(final SimulatorResult result) {
    result.print();
    HarAssert.assertAvgPageTime(result, 500);
    HarAssert.assertMaxPageTime(result, 1000)
  }
}
