package com.adviser.harhar


/**
 * @author marwol
 */
class ByteCountingOutputStream extends OutputStream {

  long bytes = 0

  void write(int b) {
    bytes++
  }

  void write(byte[] b) {
    bytes += b.length
  }

  void write(byte[] b, int off, int len) {
    bytes += len
  }
}
