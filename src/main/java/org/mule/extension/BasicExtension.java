package org.mule.extension;

import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Operations;


/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations
 * and sources are going to be declared.
 */
@Extension(name = "basic")
@Operations(BasicOperations.class)
@Configurations(BasicConfiguration.class)
public class BasicExtension {

}
