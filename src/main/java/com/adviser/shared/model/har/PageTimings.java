package com.adviser.shared.model.har;

import java.io.Serializable;

import lombok.Data;

@Data
public class PageTimings implements Serializable {
	private int onContentLoad;
	private int onLoad;
}
