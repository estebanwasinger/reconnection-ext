package org.mule.extension;

import static org.mule.extension.BasicConnectionProvider.fail;
import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;


/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class BasicOperations {

  /**
   * Example of a simple operation that receives a string parameter and returns a new string message that will be set on the payload.
   */
  public void switchConnection() {
    fail = !fail;
  }
}
