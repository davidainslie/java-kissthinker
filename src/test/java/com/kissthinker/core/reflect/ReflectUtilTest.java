package com.kissthinker.core.reflect;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


/**
 * @author David Ainslie
 *
 */
public class ReflectUtilTest
{
    /** */
    private static final String STATIC_FINAL_STRING = null;

    /** */
    private static String staticString;

    /** */
    private final String finalInstanceString = null;

    /**
     * For KISS IoC {@link Configurator} we don't want to configure/inject when "something" has been set, in this case "".
     * Setting to a value should be that valu,e because why would you set it if you want to configure/inject?
     */
    private final String finalInstanceEmptyString = "";

    /** */
    private String instanceString;

    /**
     *
     */
    public ReflectUtilTest()
    {
        super();
    }


    /**
     *
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void setStaticFinalField() throws SecurityException, NoSuchFieldException
    {
        boolean result = ReflectUtil.set(getClass().getDeclaredField("STATIC_FINAL_STRING"), "Scooby Doo");
        assertTrue(result);
        assertEquals("Scooby Doo", STATIC_FINAL_STRING);
    }


    /**
     *
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void setStaticField() throws SecurityException, NoSuchFieldException
    {
        boolean result = ReflectUtil.set(getClass().getDeclaredField("staticString"), "Scooby Doo");
        assertTrue(result);
        assertEquals("Scooby Doo", staticString);
    }


    /**
     *
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void setFinalInstanceField() throws SecurityException, NoSuchFieldException
    {
        boolean result = ReflectUtil.set(this, getClass().getDeclaredField("finalInstanceString"), "Scooby Doo");
        assertTrue(result);
        assertEquals("Scooby Doo", finalInstanceString);
    }


    /**
     * Correctly (as required by KISS IoC) the field in this test will not be set.
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void setFinalEmptyInstanceField() throws SecurityException, NoSuchFieldException
    {
        boolean result = ReflectUtil.set(this, getClass().getDeclaredField("finalInstanceEmptyString"), "Scooby Doo");
        assertTrue(result);
        assertEquals("", finalInstanceEmptyString);
    }


    /**
     * @throws NoSuchFieldException
     * @throws SecurityException
     *
     */
    @Test
    public void setInstanceField() throws SecurityException, NoSuchFieldException
    {
        boolean result = ReflectUtil.set(this, getClass().getDeclaredField("instanceString"), "Scooby Doo");
        assertTrue(result);
        assertEquals("Scooby Doo", instanceString);
    }
}