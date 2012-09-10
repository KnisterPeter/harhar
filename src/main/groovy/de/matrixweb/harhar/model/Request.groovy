package de.matrixweb.harhar.model


/**
 * @author marwol
 */
class Request implements Serializable {

  String method

  String url

  String httpVersion

  List<NameValue> headers

  List<NameValue> queryString

  List<Cookie> cookies

  int headersSize

  int bodySize

  PostData postData
}
