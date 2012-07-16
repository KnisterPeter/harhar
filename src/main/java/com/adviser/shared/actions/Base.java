package com.adviser.shared.actions;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public class Base {

  private static final Logger LOGGER = LoggerFactory.getLogger(Base.class);

  private String version = null;

  public String getServer() {
    if (version != null) {
      return version;
    }
    synchronized (this) {
      if (version != null) {
        return version;
      }
      String version = "Adviser-Cockpit(development)";
      try {
        final InputStream is = Object.class.getClassLoader().getResourceAsStream("META-INF/maven/com.adviser/loadGenerator/pom.xml");
        if (is != null) {
          DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
          Document doc;
          try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(is);
            version = "Adviser-Cockpit(" + doc.getElementsByTagName("version").item(0).getTextContent() + ")";
          } catch (Exception e) {
            // System.out.println("IS:"+e.getMessage());
          }
        }
      } catch (Exception e) {
        Base.LOGGER.error("getServer:VERSION:", e);
      }
      this.version = version;
    }
    return version;
  }

}
