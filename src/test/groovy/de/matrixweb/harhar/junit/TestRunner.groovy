package de.matrixweb.harhar.junit;

import org.junit.runner.RunWith

import de.matrixweb.harhar.junit.Har
import de.matrixweb.harhar.junit.HarAssert
import de.matrixweb.harhar.junit.HarHarRunner
import de.matrixweb.harhar.result.SimulatorResult

/**
 * @author marwol
 */
@RunWith(HarHarRunner.class)
class TestRunner {

  @Har(value = "classpath:image.har", users = 10, repetitions = 10)
  void testHar(final SimulatorResult result) {
    result.print();
    HarAssert.assertAvgPageTime(result, 500);
    HarAssert.assertMaxPageTime(result, 5000)
  }
}
