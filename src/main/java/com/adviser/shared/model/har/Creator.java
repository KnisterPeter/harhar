package com.adviser.shared.model.har;

import java.io.Serializable;

import lombok.Data;

@Data
public class Creator implements Serializable {
	private String name;
	private String version;
}
