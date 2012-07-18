package com.adviser.harhar.result

import com.adviser.harhar.model.Page

/**
 * @author marwol
 */
class PageResult {

  Page page

  List<EntryResult> entryResults

  long start = System.currentTimeMillis()

  long end

  PageResult(Page page) {
    this.page = page
  }

  long getNum() {
    entryResults.size()
  }

  long getSize() {
    entryResults.sum { it.getSize() }
  }

  Map getStatusResults() {
    entryResults.countBy { it.statusCode }
  }
}
