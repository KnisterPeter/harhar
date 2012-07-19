package com.adviser.harhar.junit

import org.junit.runner.Description
import org.junit.runner.notification.RunNotifier

class HarHarRunner extends org.junit.runner.Runner {

  HarHarRunner(Class classUnderTest) {
    super()
  }

  Description getDescription() {
    return null
  }

  void run(RunNotifier notifier) {
  }
}
