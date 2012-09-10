package de.matrixweb.harhar

import java.util.concurrent.Callable
import java.util.concurrent.CountDownLatch

import de.matrixweb.harhar.model.Har
import de.matrixweb.harhar.result.UserResult

/**
 * @author marwol
 */
class User implements Callable<List<UserResult>> {

  Logger logger

  int repetitions

  def baseUrl

  Har har

  CountDownLatch cdl

  User(logger, repetitions, baseUrl, Har har, CountDownLatch cdl) {
    this.logger = logger
    this.repetitions = repetitions
    this.baseUrl = baseUrl
    this.har = har
    this.cdl = cdl
  }

  List<UserResult> call() {
    def list = []
    try {
      Browser browser = new Browser(logger, baseUrl)
      repetitions.times { list << exec(browser) }
      browser.quit()
    } finally {
      cdl.countDown()
    }
    return list
  }

  UserResult exec(Browser browser) {
    UserResult result = new UserResult()
    result.pageResults = har.getLog().getPages().collect {
      browser.request(it, har.getLog().getEntriesByPage(it))
    }
    result.setEnd(System.currentTimeMillis())
    return result
  }
}
