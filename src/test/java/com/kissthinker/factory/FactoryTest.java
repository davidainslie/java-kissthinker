package com.kissthinker.factory;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * @author David Ainslie
 *
 */
public class FactoryTest
{
    /** */
    @Test
    public void create()
    {
        String string = Factory.create(String.class, "blah");
        assertEquals(string, "blah");
    }
}