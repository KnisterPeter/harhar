package com.adviser.shared.model;

import java.io.Serializable;

import lombok.Data;


@Data
public class LoadValue implements Serializable {
  private static final long serialVersionUID = 6371112964221712893L;
  
  
  private volatile short value = 10; /* 0-100/100 => % */
  private volatile String url = null;
  private volatile int concurrent = 10;
  private Statistics statictics = new Statistics();
}
