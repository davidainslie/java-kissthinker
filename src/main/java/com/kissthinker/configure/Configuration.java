package com.kissthinker.configure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.kissthinker.proxy.Proxy;
import com.kissthinker.system.Environment;

/**
 * Highlight a class (or interface) as being a "configuration" that can be used (injected into) a reference that is highlighted as {@link Configure}.
 *
 * Note, that if a "proxy" is provided, then "id" (if provided) is ignored.
 * 
 * @author David Ainslie
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Configuration
{
    /**
     * An interface/class name is used by default to identify a "configuration", of which this given "id" can override.
     * @return ID
     */
    String id() default "";

    /**
     *
     * @return Environment array
     */
    Environment[] environment();

    /**
     *
     * @return Proxy class
     */
    Class<? extends Proxy> proxy() default Proxy.Null.class;

    /**
     * For more than one available "configuration" within an "environment" a default (if available) will be used, else, the most specific "configuration".
     * @return True or false
     */
    boolean isDefault() default false;
}