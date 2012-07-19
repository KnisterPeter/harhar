package com.adviser.harhar.result

import java.util.concurrent.Future

import com.adviser.harhar.ByteCountingOutputStream
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

  ByteCountingOutputStream bcos

  EntryResult(Entry entry) {
    this.entry = entry
    this.bcos = new ByteCountingOutputStream()
  }

  void calculate() {
    Response r = response.get()
    this.statusCode = r.getStatusCode()
    response = null
  }

  long getTime() {
    end - start
  }

  long getSize() {
    bcos.bytes
  }
}
