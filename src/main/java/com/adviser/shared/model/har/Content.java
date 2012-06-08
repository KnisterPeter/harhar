package com.adviser.shared.model.har;

import java.io.Serializable;

import lombok.Data;

@Data
public class Content implements Serializable {
	private int size;
	private int compression;
	private String mimeType;
}
