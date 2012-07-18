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

  Map getStatusResults() {
    def merged = [:]
    pageResults.each {
      def res = it.statusResults
      res.each { k, v -> merged.get(k, []).add(v) }
    }
    merged.collectEntries { k, v -> [k, v.sum()] }
  }
}
