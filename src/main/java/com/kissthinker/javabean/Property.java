package com.kissthinker.javabean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker annotation to hightlight a "field" as a JavaBean "property" i.e. property listeners  will be informed of state changes to a marked up property field.
 * <p>
 * NOTE That a field marked up as "property" is only recognised within the context of a class marked up with @JavaBean
 * @author David Ainslie
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Property
{
    /**
     Zero based (for convenience when used with e.g. arrays, tables etc.) to indicate order.
     Default of -1 means that no index is set.
     NOTE This index is meaningless when Property is a method parameter.
     */
    int index() default -1;

    /** A (user friendly) name that can be used for anything besides logic, such as a column title in a GUI table. */
    String name() default "";
}