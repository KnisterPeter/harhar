package com.adviser.harhar.result



/**
 * @author marwol
 */
class SimulatorResult {

  List<UserResult> userResults

  long start = System.currentTimeMillis()

  long end

  long getTime() {
    end - start
  }

  long getNum() {
    userResults.sum { it.getNum() }
  }

  long getMinUserTime() {
    def times = this.userResults.collect { it.getTime() }
    times.min()
  }

  long getAvgUserTime() {
    def times = this.userResults.collect { it.getTime() }
    times.sum() / times.size()
  }

  long getMaxUserTime() {
    def times = this.userResults.collect { it.getTime() }
    times.max()
  }

  long getUserSize() {
    def sizes = this.userResults.collect { it.getSize() }
    sizes.sum() / sizes.size()
  }

  Map getStatusResults() {
    def merged = [:]
    userResults.each {
      def res = it.statusResults
      res.each { k, v -> merged.get(k, []).add(v) }
    }
    merged.collectEntries { k, v -> [k, v.sum()] }
  }

  void print() {
    println "Simulator:"
    println "  time: ${time} ms"
    println "  requests (total/per s): ${num}/${num / (time / 1000)}"
    println "  user time (min/avg/max): ${minUserTime}/${avgUserTime}/${maxUserTime} ms"
    println "  user size: ${userSize} bytes"
    println "  status: ${statusResults}"
  }
}
