package de.matrixweb.harhar.junit

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * @author marwol
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Har {

  /**
   * @return Returns the resource location of the HAR file to execute. Currently
   *         this could be a url with either <code>classpath:path/to/har</code>
   *         or <code>file:path/to/har</code>
   */
  String value()

  /**
   * @return Returns the number of users to simulate concurrently. Defaults to 1
   */
  int users() default 1

  /**
   * @return Returns the number of repetitions the HAR file should be executed.
   *         Defaults to 1
   */
  int repetitions() default 1

  /**
   * @return Returns true to enabled debug output. Defaults to false
   */
  boolean debug() default false
}
