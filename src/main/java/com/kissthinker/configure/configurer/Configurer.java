package com.kissthinker.configure.configurer;

import java.lang.reflect.Field;
import com.kissthinker.configure.Configure;
import static com.kissthinker.object.ObjectUtil.morph;

/**
 * The abstract (parent) to any "configurer" i.e how configuration is performed, where the default {@link ConfigurationConfigurer} searches for configurations on the classpath.
 * <p>
 * @author David Ainslie
 *
 */
public abstract class Configurer
{
    /** */
    private final Configurator configurator;

    /**
     *
     * @param configurator
     */
    public Configurer(Configurator configurator)
    {
        super();
        this.configurator = configurator;
    }

    /**
     *
     * @param configurable
     * @param configurableClass
     * @param field
     * @return
     */
    public boolean configure(Object configurable, Class<? extends Object> configurableClass, Field field)
    {
        return doConfigure(configurable, configurableClass, field) || configureOtherwise(configurable, field);
    }

    /**
     * Contract
     * @param configurable
     * @param configurableClass
     * @param field
     * @return
     */
    protected abstract boolean doConfigure(Object configurable, Class<? extends Object> configurableClass, Field field);

    /**
     *
     * @param configurable
     * @param field
     * @param configurationClass
     * @return
     */
    protected boolean configure(Object configurable, Field field, Class<?> configurationClass)
    {
        return configurator().configure(configurable, field, configurationClass);
    }

    /**
     *
     * @param configurable
     * @param field
     * @param configuration
     * @return
     */
    protected boolean configure(Object configurable, Field field, Object configuration)
    {
        return configurator().configureIt(configurable, field, configuration);
    }

    /**
     * The fallback.<p>
     * TODO Morphing is incomplete i.e need to implement many morphers
     * @param configurable
     * @param field
     * @return boolean true if configured otherwise false
     */
    protected boolean configureOtherwise(Object configurable, Field field)
    {
        Configure configure = field.getAnnotation(Configure.class);

        String otherwise = configure.otherwise();

        if (!"".equals(otherwise))
        {
            // Try "morphing"?
            Object morph = morph(otherwise, field.getType());

            if (morph != null)
            {
                return configurator().configureIt(configurable, field, morph);
            }
        }

        // TODO Allow in place scripting with Rhino (see version 1 of KISS IoC)

        return false;
    }

    /**
     *
     * @return
     */
    private Configurator configurator()
    {
        return configurator;
    }
}