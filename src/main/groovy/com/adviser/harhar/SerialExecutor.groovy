package com.adviser.harhar

import com.adviser.harhar.model.Har
import com.adviser.harhar.result.EntryResult
import com.adviser.harhar.result.PageResult
import com.adviser.harhar.result.SimulatorResult
import com.adviser.harhar.result.UserResult
import com.ning.http.client.AsyncHttpClient
import com.ning.http.client.AsyncHttpClientConfig
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder
import com.ning.http.client.providers.grizzly.GrizzlyAsyncHttpProvider


/**
 * @author marwol
 */
class SerialExecutor {

  void run(u, r, Har har) {
    AsyncHttpClientConfig config = new AsyncHttpClientConfig.Builder()
      .setAllowPoolingConnection(true)
      .setMaxRequestRetry(5)
      .setRequestTimeoutInMs(1000 * 60)
      .setMaximumConnectionsPerHost(10000)
      .setMaximumConnectionsTotal(10000)
      .build();
    def client = new AsyncHttpClient(new GrizzlyAsyncHttpProvider(config));

    def responses = []
    SimulatorResult sr = new SimulatorResult()
    println "Starting load test"
    (0..<u).each {
      UserResult ur = new UserResult()
      r.times {
        har.getLog().getPages().each {
          PageResult pr = new PageResult(it)
          har.getLog().getEntriesByPage(it).each {
            def url = it.request.url
            BoundRequestBuilder request
            switch (it.request.method.toLowerCase()) {
              case "get":
              request = client.prepareGet(url)
              it.request.queryString.each {
                request.addParameter(it.name, it.value)
              }
              break
              case "post":
              request = client.preparePost(url)
              request.setBody(it.request.postData.text)
              break
            }
            it.request.headers.each {
              request.addHeader(it.name, it.value)
            }
            //println "Requesting ${url}"
            EntryResult er = new EntryResult()
            er.response = request.execute(new HttpCallback(null, er))
            responses << er.response
            pr.entryResults << er
          }
          pr.end = System.currentTimeMillis()
          ur.pageResults << pr
        }
      }
      ur.end = System.currentTimeMillis()
      sr.userResults << ur
    }
    while (!responses.every {
      it.done
    }) {
      Thread.yield()
    }
    sr.userResults.each {
      it.pageResults.each {
        it.entryResults.each {
          it.calculate()
        }
      }
    }
    sr.end = System.currentTimeMillis()

    client.close()

    sr.print()
  }
}
