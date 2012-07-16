package com.adviser.shared.utils;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultHeaderFilterStrategy;

public class MyFilterStrategy extends DefaultHeaderFilterStrategy {

  public MyFilterStrategy() {
    this.getOutFilter().add("content-type");
    this.getOutFilter().add(Exchange.HTTP_RESPONSE_CODE);
    this.setLowerCase(true);
  }

}
