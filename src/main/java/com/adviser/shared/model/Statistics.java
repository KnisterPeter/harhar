package com.adviser.shared.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;

public class Statistics {

  protected Statistics() {}
	@Data
	public static class ByRequest {
		private long count = 0;
		private long bytes = 0;
		private long msecs = 0;
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
			requests.put(Integer.valueOf(UNKOWN), new ByRequest());
			requests.put(Integer.valueOf(HTTP_100), new ByRequest());
			requests.put(Integer.valueOf(HTTP_200), new ByRequest());
			requests.put(Integer.valueOf(HTTP_300), new ByRequest());
			requests.put(Integer.valueOf(HTTP_400), new ByRequest());
			requests.put(Integer.valueOf(HTTP_500), new ByRequest());
		}
		return requests;
	}

	public void incRequest(int inRetCode, int bytes, long msecs) {
	  int retCode = inRetCode;
		if (!(HTTP_100 <= retCode && retCode < HTTP_MAX)) {
			retCode = 0;
		}
		ByRequest br = getRequests().get(Integer.valueOf((retCode / HTTP_100) * HTTP_100));
		++br.count;
		br.bytes += bytes;
		br.msecs += msecs;
	}

}
