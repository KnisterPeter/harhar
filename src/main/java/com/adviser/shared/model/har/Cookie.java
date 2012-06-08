package com.adviser.shared.model.har;

import java.io.Serializable;

import lombok.Data;

@Data
public class Cookie implements Serializable {
	private String name;
	private String value;
	private String expires;
	private boolean httpOnly;
	private boolean secure;
}
