package com.kissthinker.core.collection.array;


import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.util.Arrays;

import org.junit.Test;


/**
 * @author David Ainslie
 *
 */
public class ArrayUtilTest
{
    /**
     *
     */
    public ArrayUtilTest()
    {
        super();
    }


    /**
     *
     */
    @Test
    public void convert()
    {
        // Standard way.
        Object[] objects = new Object[]{"Scooby", "Scrappy"};
        Serializable[] serializables = Arrays.copyOf(objects, objects.length, Serializable[].class);
        assertEquals("Scooby", serializables[0]);
        assertEquals("Scrappy", serializables[1]);

        // Via ArrayUtil.
        objects = new Object[]{"Scrappy", "Scooby"};
        serializables = ArrayUtil.convert(objects, Serializable[].class);
        assertEquals("Scrappy", serializables[0]);
        assertEquals("Scooby", serializables[1]);
    }
}