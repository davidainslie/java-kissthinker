package com.kissthinker.configure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allow multiple {@link Configuration} to be grouped.
 * <br/>
 * If Configurations has multiple Configuration after filtering for the environment, and there is isn't one set as "default",
 * then first declared will be used.
 * @see com.kissthinker.configure.configurer.ConfigurationConfigurer
 * @see com.kissthinker.factory.Factory
 * TODO The above 2 classes have some similar code because of the introduction of this annotation, which need refactoring.
 * @author David Ainslie
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Configurations
{
    /** */
    Configuration[] value();
}