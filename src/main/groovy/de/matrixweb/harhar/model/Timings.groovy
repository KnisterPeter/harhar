package de.matrixweb.harhar.model


/**
 * @author marwol
 */
class Timings implements Serializable {

  int blocked

  int dns

  int connect

  int send

  int wait

  int receive

  int ssl
}
