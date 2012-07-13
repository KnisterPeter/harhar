package com.adviser.shared.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Statistics {

  protected Statistics() {
  }

  public static class ByRequest {

    private long count = 0;

    private long bytes = 0;

    private long msecs = 0;

    /**
     * @return the count
     */
    public final long getCount() {
      return count;
    }

    /**
     * @param count
     *          the count to set
     */
    public final void setCount(final long count) {
      this.count = count;
    }

    /**
     * @return the bytes
     */
    public final long getBytes() {
      return bytes;
    }

    /**
     * @param bytes
     *          the bytes to set
     */
    public final void setBytes(final long bytes) {
      this.bytes = bytes;
    }

    /**
     * @return the msecs
     */
    public final long getMsecs() {
      return msecs;
    }

    /**
     * @param msecs
     *          the msecs to set
     */
    public final void setMsecs(final long msecs) {
      this.msecs = msecs;
    }

  }

  private Map<Integer, ByRequest> requests;

  private final static int UNKOWN = 0;
  private final static int HTTP_100 = 100;
  private final static int HTTP_200 = 200;
  private final static int HTTP_300 = 300;
  private final static int HTTP_400 = 400;
  private final static int HTTP_500 = 500;
  private final static int HTTP_MAX = 600;

  public Map<Integer, ByRequest> getRequests() {
    if (requests == null) {
      requests = new ConcurrentHashMap<Integer, ByRequest>();
      requests.put(Integer.valueOf(Statistics.UNKOWN), new ByRequest());
      requests.put(Integer.valueOf(Statistics.HTTP_100), new ByRequest());
      requests.put(Integer.valueOf(Statistics.HTTP_200), new ByRequest());
      requests.put(Integer.valueOf(Statistics.HTTP_300), new ByRequest());
      requests.put(Integer.valueOf(Statistics.HTTP_400), new ByRequest());
      requests.put(Integer.valueOf(Statistics.HTTP_500), new ByRequest());
    }
    return requests;
  }

  public void incRequest(final int inRetCode, final int bytes, final long msecs) {
    int retCode = inRetCode;
    if (!(Statistics.HTTP_100 <= retCode && retCode < Statistics.HTTP_MAX)) {
      retCode = 0;
    }
    ByRequest br = this.getRequests().get(Integer.valueOf(retCode / Statistics.HTTP_100 * Statistics.HTTP_100));
    ++br.count;
    br.bytes += bytes;
    br.msecs += msecs;
  }

}
