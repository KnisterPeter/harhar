package com.adviser.loadGenerator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	//@SuppressWarnings("unused")
	private ApplicationContext applicationContext;

	public Router(String[] args, ApplicationContext ac) {
		applicationContext = ac;
		listenaddress = ac.getBean("ListenAddress", ListenAddress.class);
		if (args.length == 1) {
			listenaddress.setAddr(args[0]);
		} else if (args.length >= 2) {
			listenaddress.setAddr(args[0]);
			listenaddress.setPort(args[1]);
		}
	}

	// public void setLoadGeneratorValue(Exchange exchange) {
	// try {
	// loadGenerator = (new ObjectMapper()).readValue(IOUtils
	// .toByteArray((InputStream) exchange.getIn().getBody()),
	// LoadGenerator.class);
	// LOGGER.debug("loadGenerator: {}", loadGenerator);
	// } catch (Exception e) {
	// LOGGER.error("setLoadGeneratorValue", e);
	// }
	// }

	public void configure() {
		LOGGER.info("Version:" + new Base().getServer());
		LOGGER.info("Listen On:" + listenaddress.toString());

		final String restlet = "jetty:http://" + listenaddress.toString();

		final LoadGenerator loadGenerator = applicationContext.getBean(
				"LoadGenerator", LoadGenerator.class);

		onException(Exception.class).maximumRedeliveries(0).bean(loadGenerator, "exception").handled(true);

		from(restlet + "/loadGenerator/get").bean(loadGenerator, "get");
		from(restlet + "/loadGenerator/put").bean(loadGenerator, "put");	
		from(restlet + "/loadGenerator/addhar")
			.unmarshal()
			.json(JsonLibrary.Jackson, Har.class)
			.bean(loadGenerator, "addHar")
			.marshal()
			.json(JsonLibrary.Jackson);

		
		from(restlet + "/loadGenerator/remPage").bean(loadGenerator, "remPage");
		
		from(restlet + "/loadGenerator/pages")
			.bean(loadGenerator, "pages")
			.marshal()
			.json(JsonLibrary.Jackson);
		
		from(restlet + "?matchOnUriPrefix=true").beanRef("Files", "page");

		//final AtomicInteger count = new AtomicInteger(0);
		ExecutorService es = Executors.newFixedThreadPool(200);
		from("timer://loadGenerator?period=1")
			.bean(loadGenerator, "generator")
			.choice()
				.when(property("processEntries").isNull())
			.otherwise().to("seda://issueRequests");
		
		from("seda://issueRequests?limitConcurrentConsumers=false&concurrentConsumers=100")
			.bean(loadGenerator, "processEntries")
		    .bean(loadGenerator, "completed")
			.end();
	}

}
