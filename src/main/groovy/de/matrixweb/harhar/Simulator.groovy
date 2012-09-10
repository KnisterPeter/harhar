package de.matrixweb.harhar

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

import org.apache.commons.lang3.concurrent.BasicThreadFactory

import de.matrixweb.harhar.model.Har
import de.matrixweb.harhar.result.SimulatorResult
import de.matrixweb.harhar.result.UserResult

/**
 * @author marwol
 */
class Simulator {

  Logger logger

  int users

  int repetitions

  def baseUrl

  def result

  Simulator(logger, int users, int repetitions, def url) {
    this.logger = logger
    this.users = users
    this.repetitions = repetitions
    this.baseUrl = url
  }

  void run(Har har) {
    ThreadPoolExecutor threadPool = Executors.newFixedThreadPool(users, new BasicThreadFactory.Builder().namingPattern("user-%d").daemon(true).build())
    CountDownLatch cdl = new CountDownLatch(users)
    result = new SimulatorResult(logger)
    List<Future<List<UserResult>>> list = (0..<users).collect { threadPool.submit(new User(logger, repetitions, baseUrl, har, cdl)) }
    while (cdl.count > 0) {
      logger.debug("Simulator CDL ${cdl.count}")
      cdl.await(5, TimeUnit.SECONDS)
    }
    result.setEnd(System.currentTimeMillis())
    threadPool.shutdown()
    result.userResults = []
    result.userResults.addAll(list.collect { it.get() }.flatten())
  }

}
