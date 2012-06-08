package com.adviser.shared.model.har;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Response implements Serializable {
	private int status;
	private String statusText;
	private String httpVersion;
	private List<NameValue> headers;
	private List<Cookie> cookies;
	private Content content;
	private String redirectURL;
	private int headersSize;
	private int bodySize;
}
