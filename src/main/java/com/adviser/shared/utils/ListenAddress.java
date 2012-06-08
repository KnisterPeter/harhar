package com.adviser.shared.utils;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("ListenAddress")
@Data
public class ListenAddress {
    private String addr = "127.0.0.1";
    private String port = "10201";

    @Value("${com.adviser.server.listen}")
    public void setAddressPort(String str) {
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

    public ListenAddress(String port, String addr) {
      this.port = port;
      if (addr != null) {
        this.addr = addr;
      }
    }

    public String toString() {
      return addr + ":" + port;
    }
  }
