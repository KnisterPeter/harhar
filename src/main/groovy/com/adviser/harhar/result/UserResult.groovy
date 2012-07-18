package com.adviser.harhar.result

/**
 * @author marwol
 */
class UserResult {

  List<PageResult> pageResults

  long start = System.currentTimeMillis()

  long end

  long getNum() {
    pageResults.sum { it.getNum() }
  }

  long getTime() {
    end - start
  }

  long getSize() {
    pageResults.sum { it.getSize() }
  }
}
