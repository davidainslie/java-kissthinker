package com.kissthinker.core.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.kissthinker.core.system.Environment;

/**
 * Any object can be marked up as a "service". Services should be automatically started to "service" other objects.
 * <br/>
 * @author David Ainslie
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Service
{
    /** */
    Environment[] environment();
}