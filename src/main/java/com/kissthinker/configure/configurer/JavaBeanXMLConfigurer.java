package com.kissthinker.configure.configurer;

import java.lang.reflect.Field;

/**
 * @author David Ainslie
 *
 */
public class JavaBeanXMLConfigurer extends Configurer
{
    /**
     *
     * @param configurator Configurator
     */
    public JavaBeanXMLConfigurer(Configurator configurator)
    {
        super(configurator);
        // TODO Auto-generated constructor stub
    }

    /**
     *
     * @see com.kissthinker.configure.configurer.Configurer#doConfigure(java.lang.Object, java.lang.Class, java.lang.reflect.Field)
     */
    @Override
    protected boolean doConfigure(Object configurable, Class<? extends Object> configurableClass, Field field)
    {
        // Check system property via configure.id() Given a XML Java configuration file? TODO

        // TODO Read in from "property file" in this case adding in the xmldecoder/encoder to Factory so that declarations can be made in
        // <package name>FieldTypeName-dev.xml

        // TODO Auto-generated method stub
        return false;
    }
}