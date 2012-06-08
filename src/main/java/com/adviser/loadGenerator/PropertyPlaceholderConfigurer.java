package com.adviser.loadGenerator;

import org.springframework.core.io.FileSystemResource;

public class PropertyPlaceholderConfigurer extends
    org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {

  public PropertyPlaceholderConfigurer() {
    super();
    final String path = System.getProperty("com.adviser.loadGenerator.config", "loadGenerator.properties");
    setLocation(new FileSystemResource(path));
  }

}
