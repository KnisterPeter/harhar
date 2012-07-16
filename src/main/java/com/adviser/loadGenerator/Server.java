package com.adviser.loadGenerator;

import org.apache.camel.CamelContext;
import org.apache.camel.component.jetty.JettyHttpComponent;
import org.apache.camel.spi.HeaderFilterStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.adviser.shared.utils.MyFilterStrategy;

public final class Server {

  private static final Logger LOGGER = LoggerFactory.getLogger(Router.class);

  private Server() {
  }

  public static void main(final String[] args) {
    final ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:loadGenerator.xml");
    final CamelContext camelContext = ac.getBean("camelContext", CamelContext.class);

    camelContext.disableJMX();
    final JettyHttpComponent jhc = new JettyHttpComponent();
    final HeaderFilterStrategy hfs = new MyFilterStrategy();
    jhc.setHeaderFilterStrategy(hfs);
    camelContext.addComponent("jetty", jhc);
    camelContext.getExecutorServiceStrategy().getDefaultThreadPoolProfile().setMaxPoolSize(200);
    try {
      camelContext.addRoutes(new Router(args, ac));
      camelContext.start();
    } catch (Exception e) {
      Server.LOGGER.error("camelContext", e);
    }
  }

}
