package com.kissthinker.core.configure.configurer;

import java.lang.reflect.Field;

/**
 * @author David Ainslie
 *
 */
public class IoCConfigurer extends Configurer
{
    /**
     *
     * @param configurator
     */
    public IoCConfigurer(Configurator configurator)
    {
        super(configurator);
        // TODO Auto-generated constructor stub
    }

    /**
     *
     * @see com.kissthinker.core.configure.configurer.Configurer#doConfigure(java.lang.Object, java.lang.Class, java.lang.reflect.Field)
     */
    @Override
    protected boolean doConfigure(Object configurable, Class<? extends Object> configurableClass, Field field)
    {
        // Check system property with configure.id() Given a ".ioc" configuration script file? TODO

        // TODO Auto-generated method stub
        return false;
    }
}