package com.adviser.loadGenerator;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.adviser.shared.actions.Base;
import com.adviser.shared.model.har.Har;
import com.adviser.shared.utils.ListenAddress;

public class Router extends RouteBuilder {

  private static final Logger LOGGER = LoggerFactory.getLogger(Router.class);

  private ListenAddress listenaddress = null;

  private final ApplicationContext applicationContext;

  public Router(final String[] args, final ApplicationContext ac) {
    applicationContext = ac;
    listenaddress = ac.getBean("ListenAddress", ListenAddress.class);
    if (args.length == 1) {
      listenaddress.setAddr(args[0]);
    } else if (args.length >= 2) {
      listenaddress.setAddr(args[0]);
      listenaddress.setPort(args[1]);
    }
  }

  @Override
  public void configure() {
    Router.LOGGER.info("Version:" + new Base().getServer());
    Router.LOGGER.info("Listen On:" + listenaddress.toString());

    final String restlet = "jetty:http://" + listenaddress.toString();

    final LoadGenerator loadGenerator = applicationContext.getBean("LoadGenerator", LoadGenerator.class);

    // @formatter:off
    this.onException(Exception.class)
      .maximumRedeliveries(0)
      .bean(loadGenerator, "exception")
      .handled(true);

    this.from(restlet + "/loadGenerator/get")
      .bean(loadGenerator, "get");
    this.from(restlet + "/loadGenerator/put")
      .bean(loadGenerator, "put");
    this.from(restlet + "/loadGenerator/addhar")
      .unmarshal().json(JsonLibrary.Jackson, Har.class)
      .bean(loadGenerator, "addHar")
      .marshal().json(JsonLibrary.Jackson);
    this.from(restlet + "/loadGenerator/remPage")
      .bean(loadGenerator, "remPage");
    this.from(restlet + "/loadGenerator/pages")
      .bean(loadGenerator, "pages")
      .marshal().json(JsonLibrary.Jackson);
    this.from(restlet + "?matchOnUriPrefix=true")
      .beanRef("Files", "page");
    this.from("timer://loadGenerator?period=1")
      .bean(loadGenerator, "generator")
      .choice()
        .when(this.property("processEntries").isNull())
        .otherwise()
          .to("seda://issueRequests");
    this.from("seda://issueRequests?limitConcurrentConsumers=false&concurrentConsumers=100")
      .bean(loadGenerator, "processEntries")
      .bean(loadGenerator, "completed")
      .end();
    // @formatter:on
  }

}
