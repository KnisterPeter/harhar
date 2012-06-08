package com.adviser.shared.model.har;

import java.io.Serializable;

import lombok.Data;

@Data
public class Har implements Serializable {
	public void addHar(Har har) {
		log.addLog(har.getLog());
	}
	private Log log = new Log();
}
