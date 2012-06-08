package com.adviser.shared.model.har;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

@Data
public class Entry implements Serializable {
	private String pageref;
	private String startedDateTime;
	private int time;
	private Request request;
	private Response response;
	//private Cache cache;
	private Map<String, String> cache;
	private Timings timings;
}
