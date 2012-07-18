package com.adviser.harhar.result

import java.util.concurrent.Future

import com.adviser.harhar.model.Entry
import com.ning.http.client.Response

/**
 * @author marwol
 */
class EntryResult {

  Entry entry;

  long start = System.currentTimeMillis()

  long end

  Future<Response> response

  int statusCode

  long size = -1

  EntryResult(Entry entry) {
    this.entry = entry
  }

  void calculate() {
    Response r = response.get()
    this.statusCode = r.getStatusCode()
    this.size = r.getResponseBodyAsBytes().size()
    response = null
  }

  long getTime() {
    end - start
  }
}
