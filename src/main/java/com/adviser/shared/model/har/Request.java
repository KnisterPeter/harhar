package com.adviser.shared.model.har;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Request implements Serializable {
	private String method;
	private String url;
	private String httpVersion;
	private List<NameValue> headers;
	private List<NameValue> queryString;
	private List<Cookie> cookies;
	private int headersSize;
	private int bodySize;
	private PostData postData;
}
