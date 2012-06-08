package com.adviser.shared.model.har;

import java.io.Serializable;

import lombok.Data;

@Data
public class Page implements Serializable {
	private String startedDateTime;
	private String id;
	private String title;
	private PageTimings pageTimings;
}
