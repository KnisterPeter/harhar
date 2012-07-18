package com.adviser.harhar

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

import org.apache.commons.lang3.concurrent.BasicThreadFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.adviser.harhar.model.Har
import com.adviser.harhar.result.SimulatorResult
import com.adviser.harhar.result.UserResult

/**
 * @author marwol
 */
class Simulator {

  static final Logger LOGGER = LoggerFactory.getLogger(Simulator.class)

  int users

  int repetitions

  def baseUrl

  def serial

  Simulator(int users, int repetitions, def url, serial) {
    this.users = users
    this.repetitions = repetitions
    this.baseUrl = url
    this.serial = serial
  }

  void run(Har har) {
    if (serial) {
      new SerialExecutor().run(users, repetitions, har)
    } else {
      runMultiThreaded(har)
    }
  }

  void runMultiThreaded(Har har) {
    ThreadPoolExecutor threadPool = Executors.newFixedThreadPool(users, new BasicThreadFactory.Builder().namingPattern("user-%d").daemon(true).build())
    CountDownLatch cdl = new CountDownLatch(users)
    SimulatorResult result = new SimulatorResult()
    List<Future<List<UserResult>>> list = (0..<users).collect { threadPool.submit(new User(repetitions, baseUrl, har, cdl)) }
    while (cdl.count > 0) {
      LOGGER.debug("Simulator CDL {}", cdl.count)
      cdl.await(5, TimeUnit.SECONDS)
    }
    result.setEnd(System.currentTimeMillis())
    threadPool.shutdown()
    result.userResults = []
    result.userResults.addAll(list.collect { it.get() }.flatten())
    result.print()
  }
}
