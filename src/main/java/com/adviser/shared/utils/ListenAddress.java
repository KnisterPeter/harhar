package com.adviser.shared.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("ListenAddress")
public class ListenAddress {

  private String addr = "127.0.0.1";

  private String port = "10201";

  /**
   * @return the addr
   */
  public final String getAddr() {
    return addr;
  }

  /**
   * @param addr
   *          the addr to set
   */
  public final void setAddr(final String addr) {
    this.addr = addr;
  }

  /**
   * @return the port
   */
  public final String getPort() {
    return port;
  }

  /**
   * @param port
   *          the port to set
   */
  public final void setPort(final String port) {
    this.port = port;
  }

  @Value("${com.adviser.server.listen}")
  public void setAddressPort(final String str) {
    String[] parts = str.split(":");
    if (parts.length == 0) {
      addr = str;
    } else {
      addr = parts[0];
      port = parts[1];
    }
  }

  public ListenAddress() {
  }

  public ListenAddress(final String port, final String addr) {
    this.port = port;
    if (addr != null) {
      this.addr = addr;
    }
  }

  @Override
  public String toString() {
    return addr + ":" + port;
  }
}
