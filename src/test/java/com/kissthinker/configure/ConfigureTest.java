package com.kissthinker.configure;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * IoC test i.e testing configuration of "beans" via KISS IoC.
 * <br/>
 * "Wiring" up is done per environment; appropriate configurations are found at runtime with any "disputes" worked out via "isDefault" on a {@link Configuration},
 * and if necessary, the most specific environment choice. Note that it is encouraged to just mark up a field that requires a configuration with an empty {@link Configure}.
 * However, an "id" and/or "otherwise" can be provided. There are downsides to using these two attributes. One, regarding "id", is that a string must be given.
 * It would be nice, and this maybe a TODO, to instead have to provide an enum. However, strings (currently) are the most appropriate, as these would be stipulated
 * as system properties, or declared in properties files.
 * @author David Ainslie
 *
 */
public class ConfigureTest
{
    /** */
    @Test
    public void configure()
    {
        Bean1 bean1 = new Bean1();
        assertTrue(Bean1.staticBean2() instanceof Bean2); // Could be any type of Bean2
        assertTrue(bean1.bean2() instanceof Bean2); // Could be any type of Bean2
        assertTrue(bean1.notToBeReplacedBean2() instanceof NotToBeReplacedBean2);
        assertEquals(99, Bean1.integer().intValue());
    }

    /** */
    @Test
    public void configureOverrideWithSystemPropertyByID()
    {
        System.setProperty("systemPropertyBeanByID", "com.kissthinker.core.configure.SystemPropertyByIDBean2");
        Bean1 bean1 = new Bean1();
        assertTrue(bean1.bean2() instanceof SystemPropertyByIDBean2);
        System.setProperty("systemPropertyBeanByID", "");
    }

    /** */
    @Test
    public void configureOverrideWithSystemPropertyContract()
    {
        System.setProperty(Bean2.class.getName(), "com.kissthinker.core.configure.SystemPropertyByContractBean2");
        Bean1 bean1 = new Bean1();
        assertTrue(bean1.bean2() instanceof SystemPropertyByContractBean2);
        System.setProperty(Bean2.class.getName(), "");
    }

    /** */
    @Test
    public void configureOverrideWithProperiesFile()
    {
        Bean3 bean3 = new Bean3();
        System.out.println(bean3);
        assertEquals("property1 from properties file", Bean3.staticString());
        assertEquals(66, bean3.integer().intValue());
    }
}