package com.kissthinker.configure;

import org.junit.Test;
import static com.kissthinker.configure.configurer.ConfigurationClasses.configurationClasses;
import static com.kissthinker.system.Environment.LIVE;
import static org.junit.Assert.*;

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
        assertTrue(configurationClasses().stream().filter(Bean2.class::isAssignableFrom).findFirst().isPresent());
    }

    @Test
    public void notExists()
    {
        assertFalse(configurationClasses().stream().filter(BeanInWrongEnvironment.class::isAssignableFrom).findFirst().isPresent());
    }
}

@Configuration(environment = LIVE)
class BeanInWrongEnvironment
{
    /** */
    public BeanInWrongEnvironment()
    {
        super();
    }
}