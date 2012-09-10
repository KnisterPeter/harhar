package de.matrixweb.harhar.model

/**
 * @author marwol
 */
class Entry implements Serializable {

  String pageref

  String startedDateTime

  int time

  Request request

  Response response

  Map<String, String> cache

  Timings timings
}
