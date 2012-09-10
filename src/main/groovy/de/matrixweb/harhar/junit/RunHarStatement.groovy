package de.matrixweb.harhar.junit

import org.codehaus.jackson.map.ObjectMapper

import de.matrixweb.harhar.Logger
import de.matrixweb.harhar.Simulator


/**
 * @author marwol
 */
class RunHarStatement extends org.junit.runners.model.Statement {

  Logger logger

  Closure invocation

  String resource

  def users

  def repetitions

  RunHarStatement(Closure invocation, Har har) {
    logger = new JUnitLogger(har.debug())
    this.invocation = invocation
    resource = har.value()
    users = har.users()
    repetitions = har.repetitions()
  }

  void evaluate() {
    def sim = new Simulator(logger, users, repetitions, false)
    sim.run(new ObjectMapper().readValue(loadResource(), de.matrixweb.harhar.model.Har.class))
    invocation.call(sim.result)
  }

  InputStream loadResource() {
    def prefixIdx = resource.indexOf(':')
    switch (resource.substring(0, prefixIdx)) {
      case "classpath":
        return getClass().getClassLoader().getResourceAsStream(resource.substring(prefixIdx + 1))
      case "file":
        return new FileInputStream(resource.substring(prefixIdx + 1))
    }
    throw new IllegalArgumentException("Unknown protocol " + resource.substring(0, prefixIdx))
  }
}
