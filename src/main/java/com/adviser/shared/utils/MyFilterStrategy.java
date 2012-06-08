package com.adviser.shared.utils;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultHeaderFilterStrategy;

public class MyFilterStrategy extends DefaultHeaderFilterStrategy {
  public MyFilterStrategy() {
    getOutFilter().add("content-type");
    getOutFilter().add(Exchange.HTTP_RESPONSE_CODE);
    setLowerCase(true);
  }

}
