package de.matrixweb.harhar.model


/**
 * @author marwol
 */
class Cookie implements Serializable{

  String name

  String value

  String expires

  boolean httpOnly

  boolean secure

  String path
}
