package com.adviser.shared.model.har;

import java.io.Serializable;

public class Timings implements Serializable {

  private int blocked;

  private int dns;

  private int connect;

  private int send;

  private int wait;

  private int receive;

  private int ssl;

  /**
   * @return the blocked
   */
  public final int getBlocked() {
    return blocked;
  }

  /**
   * @param blocked
   *          the blocked to set
   */
  public final void setBlocked(final int blocked) {
    this.blocked = blocked;
  }

  /**
   * @return the dns
   */
  public final int getDns() {
    return dns;
  }

  /**
   * @param dns
   *          the dns to set
   */
  public final void setDns(final int dns) {
    this.dns = dns;
  }

  /**
   * @return the connect
   */
  public final int getConnect() {
    return connect;
  }

  /**
   * @param connect
   *          the connect to set
   */
  public final void setConnect(final int connect) {
    this.connect = connect;
  }

  /**
   * @return the send
   */
  public final int getSend() {
    return send;
  }

  /**
   * @param send
   *          the send to set
   */
  public final void setSend(final int send) {
    this.send = send;
  }

  /**
   * @return the wait
   */
  public final int getWait() {
    return wait;
  }

  /**
   * @param wait
   *          the wait to set
   */
  public final void setWait(final int wait) {
    this.wait = wait;
  }

  /**
   * @return the receive
   */
  public final int getReceive() {
    return receive;
  }

  /**
   * @param receive
   *          the receive to set
   */
  public final void setReceive(final int receive) {
    this.receive = receive;
  }

  /**
   * @return the ssl
   */
  public final int getSsl() {
    return ssl;
  }

  /**
   * @param ssl
   *          the ssl to set
   */
  public final void setSsl(final int ssl) {
    this.ssl = ssl;
  }

}
