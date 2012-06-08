package com.adviser.shared.model.har;

import java.io.Serializable;

import lombok.Data;

@Data
public class Timings implements Serializable {
	private int blocked;
	private int dns;
	private int connect;
	private int send;
	private int wait;
	private int receive;
	private int ssl;
}
