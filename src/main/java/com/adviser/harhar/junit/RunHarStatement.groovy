package com.adviser.harhar.junit

import org.codehaus.jackson.map.ObjectMapper

import com.adviser.harhar.Simulator


/**
 * @author marwol
 */
class RunHarStatement extends org.junit.runners.model.Statement {

  Closure invocation

  String resource

  def users

  def repetitions

  RunHarStatement(Closure invocation, Har har) {
    this.invocation = invocation
    resource = har.value()
    users = har.users()
    repetitions = har.repetitions()
  }

  void evaluate() {
    def sim = new Simulator(users, repetitions, false)
    sim.run(new ObjectMapper().readValue(loadResource(), com.adviser.harhar.model.Har.class))
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
