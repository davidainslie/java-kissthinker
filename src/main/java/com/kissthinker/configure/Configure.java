package com.kissthinker.configure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author David Ainslie
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Configure
{
    /**
     * If no ID is provided, then an appropriate {@link Configuration} is chosen by class
     * @return ID
     */
    String id() default "";

    /**
     * A convenient fallback as a last resort to configure by the given value (which would need to be morphed) or xml or ioc file name or in-place ioc script.
     * @return String
     */
    String otherwise() default "";
}