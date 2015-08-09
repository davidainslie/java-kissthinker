package com.kissthinker.configure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.kissthinker.proxy.Proxy;
import com.kissthinker.system.Environment;

/**
 * Highlight a class (or interface) as being a "configuration" that can be used (injected into) a reference that is highlighted as {@link Configure}.
 * <br/>
 * Note, that if a "proxy" is provided, then "id" (if provided) is ignored.
 * 
 * @author David Ainslie
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Configuration
{
    /** An interface/class name is used by default to identify a "configuration", of which this given "id" can override. */
    String id() default "";

    /** */
    Environment[] environment();

    /** */
    Class<? extends Proxy> proxy() default Proxy.Null.class;

    /** For more than one available "configuration" within an "environment" a default (if available) will be used, else, the most specific "configuration". */
    boolean isDefault() default false;
}