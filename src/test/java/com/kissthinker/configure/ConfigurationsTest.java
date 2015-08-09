package com.kissthinker.configure;

import org.junit.Test;
import com.kissthinker.configure.configurer.ConfigurationClasses;
import static org.junit.Assert.assertTrue;

/**
 * @author David Ainslie
 *
 */
public class ConfigurationsTest
{
    /** */
    public ConfigurationsTest()
    {
        super();
    }

    /** */
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