package org.example.aspectj.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
@Documented
@Inherited
public @interface GhostTracerReader {

    Class<?> connectorClass() default Void.class;

    Class<?> restConnectorReturnType() default Void.class;

    Class<?> restConnectorGenericReturnType() default Void.class;

    IgnoreInputAttributeTable[] ignoreInputAttributesClass() default {};
}
