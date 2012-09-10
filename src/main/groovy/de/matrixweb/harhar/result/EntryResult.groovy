package de.matrixweb.harhar.result

import java.util.concurrent.Future

import com.ning.http.client.Response

import de.matrixweb.harhar.ByteCountingOutputStream
import de.matrixweb.harhar.model.Entry

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
