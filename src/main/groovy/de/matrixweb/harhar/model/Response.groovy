package de.matrixweb.harhar.model

/**
 * @author marwol
 */
class Response implements Serializable {

  int status

  String statusText

  String httpVersion

  List<NameValue> headers

  List<Cookie> cookies

  Content content

  String redirectURL

  int headersSize

  int bodySize
}
