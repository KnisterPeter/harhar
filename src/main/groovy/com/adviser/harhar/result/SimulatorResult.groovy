package com.adviser.harhar.result

import com.adviser.harhar.Logger

/**
 * @author marwol
 */
class SimulatorResult {

  Logger logger

  List<UserResult> userResults = []

  long start = System.currentTimeMillis()

  long end

  SimulatorResult(Logger logger) {
    this.logger = logger
  }

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

  long getMinRequestTime() {
    userResults.collect { it.minRequestTime }.min()
  }


  long getAvgRequestTime() {
    def times = userResults.collect { it.avgRequestTime }
    times.sum() / times.size()
  }

  long getMaxRequestTime() {
    userResults.collect { it.maxRequestTime }.max()
  }
  void print() {
    logger.info("Simulator:")
    logger.info("  time: ${time} ms")
    logger.info("  requests (total/per s): ${num}/${num / (time / 1000)}")
    logger.info("  request time (min/avg/max): ${minRequestTime}/${avgRequestTime}/${maxRequestTime} ms")
    logger.info("  user time (min/avg/max): ${minUserTime}/${avgUserTime}/${maxUserTime} ms")
    logger.info("  user size: ${userSize} bytes")
    logger.info("  status: ${statusResults}")
  }
}
