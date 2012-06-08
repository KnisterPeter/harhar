package com.adviser.loadGenerator;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.adviser.shared.model.LoadValue;
import com.adviser.shared.model.har.Entry;
import com.adviser.shared.model.har.Har;
import com.adviser.shared.model.har.Page;

@Component("LoadGenerator")
public class LoadGenerator {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LoadGenerator.class);

	private LoadValue loadValue = new LoadValue();

	@Value("${com.adviser.loadGenerator.defaultURL}")
	public void setDefaultUrl(String url) {
		loadValue.setUrl(url);
		//updateUrls();
	}

	@Value("${com.adviser.loadGenerator.concurrent}")
	public void setConcurrent(String val) {
		LOGGER.info("setConcurrent:" + val);
		loadValue.setConcurrent(Integer.parseInt(val));
		//updateUrls();
	}

	public void get(Exchange exchange) {
		final StringWriter str = new StringWriter();
		try {
			(new ObjectMapper()).writeValue(str, loadValue);
		} catch (Exception e) {
			LOGGER.error("LoadGenerator:get:", e);
		}
		try {
			final int sleep = Integer.parseInt((String) exchange.getIn()
					.getHeaders().get("sleep"));
			Thread.sleep(sleep);
		} catch (Exception e) {

		}

		exchange.getOut().setHeader("content-type", "application/json");
		exchange.getOut().setBody(str.toString());
	}
	/*
	private List<String> urls = null;

	private void updateUrls() {
		if (loadValue.getConcurrent() <= 0) {
			urls = null;
			return;
		}
		String url = loadValue.getUrl();
		if (url == null) {
			url = "http://localhost/~menabe/";
		}
		urls = new LinkedList<String>();
		for (int i = loadValue.getConcurrent() - 1; i >= 0; --i) {
			urls.add(url);
		}
		if (urls.isEmpty()) {
			urls = null;
		}
	}
	*/
	public void put(Exchange exchange) {
		try {
			loadValue.setValue(Short.parseShort((String) exchange.getIn()
					.getHeaders().get("value")));
		} catch (Exception e) {

		}
		try {
			loadValue.setConcurrent(Integer.parseInt((String) exchange.getIn()
					.getHeaders().get("concurrent")));
		} catch (Exception e) {

		}
		String url = (String) exchange.getIn().getHeaders().get("url");
		if (url != null && !url.isEmpty()) {
			loadValue.setUrl(url);
		}
		//updateUrls();
		get(exchange);
	}

	public void exception(Exchange exchange) {
		LOGGER.debug("hallo");
		final Message in = exchange.getIn();
		final Date startTime = in.getHeader("firedTime", Date.class);
		final long now = System.currentTimeMillis();
		loadValue.getStatictics().incRequest(0, 0, now - startTime.getTime());
	}

	private int counter = 0;
	private Har har = new Har();
	
	public void addHar(Exchange exchange) {
		final Message in = exchange.getIn();
		har.addHar(in.getBody(Har.class));
		exchange.getOut().setBody(har);
	}
	
	public void remPage(Exchange exchange) {
		
		//har.getLog().remPage(exchange.getIn().getBody(Har.class).getLog().);
	}
	
	public void pages(Exchange exchange) {
		exchange.getOut().setBody(har);
//		from(restlet + "/loadGenerator/pages").bean(loadGenerator, "pages")
	}
	private volatile int pagesIndex = 0;
	public void generator(Exchange exchange) {
		exchange.setProperty("processEntries", null);
		if (loadValue == null || ++counter < loadValue.getValue()) {
			return;
		}
		final int size = har.getLog().getPages().size();
		if (pagesIndex >= size) {
			pagesIndex = 0;
		}
		if (pagesIndex < har.getLog().getPages().size()) {
			Page page = har.getLog().getPages().get(pagesIndex++);
			//LOGGER.info("Page:{} {}", pagesIndex, page.getId());
			List<Entry> entries = har.getLog().getEntriesByPage(page);
			exchange.setProperty("processEntries", entries);
		} 		
		counter = 0;
	}
	EntriesRequestor entriesRequestor = new EntriesRequestor();
	
	public void processEntries(Exchange exchange) {
		List<Entry> entries = exchange.getProperty("processEntries", List.class);
		entriesRequestor.request(entries);
	}

	public void completed(Exchange exchange) throws IOException {
		/*
		 * int i = count.addAndGet(1); LOGGER.info("XXX:"+i);
		 * Thread.sleep(1000); LOGGER.info("YYY:"+i); count.addAndGet(-1);
		 */
		Message in = exchange.getIn();
		final Date startTime = in.getHeader("firedTime", Date.class);
		final long now = System.currentTimeMillis();
		final InputStream is = in.getBody(InputStream.class);
		int responseCode = in.getHeader(Exchange.HTTP_RESPONSE_CODE,
				Integer.class);
		int bytes = IOUtils.toByteArray(is).length;
		IOUtils.closeQuietly(is);
		long msecs = now - startTime.getTime();
		loadValue.getStatictics()
				.incRequest(responseCode, bytes, msecs);

	}

}
