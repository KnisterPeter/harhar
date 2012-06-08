package com.adviser.shared.model.har;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PostData implements Serializable {
	private String mimeType;
	private String text;
	private List<NameValue> params;
}
