package com.kissthinker.core.configure;


import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.kissthinker.core.configure.configurer.ConfigurationClasses;


/**
 * @author David Ainslie
 *
 */
public class ConfigurationsTest
{
    /**
     *
     */
    public ConfigurationsTest()
    {
        super();
    }


    /**
     *
     */
    @Test
    public void exists()
    {
        boolean bean2ClassFound = false;

        for (Class<?> configurationClass : ConfigurationClasses.configurationClasses())
        {
            if (Bean2.class.isAssignableFrom(configurationClass))
            {
                bean2ClassFound = true;
            }
        }

        assertTrue(bean2ClassFound);
    }
}