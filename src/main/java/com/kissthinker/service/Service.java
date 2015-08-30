package com.kissthinker.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.kissthinker.system.Environment;

/**
 * Any object can be marked up as a "service". Services should be automatically started to "service" other objects.
 *
 * @author David Ainslie
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Service
{
    /**
     *
     * @return Environment array
     */
    Environment[] environment();
}