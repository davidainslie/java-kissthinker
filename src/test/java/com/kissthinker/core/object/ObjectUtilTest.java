package com.kissthinker.core.object;


import static org.junit.Assert.assertEquals;

import java.awt.Dimension;

import org.junit.Test;


/**
 * @author David Ainslie
 *
 */
public class ObjectUtilTest
{
    /**
     *
     */
    public ObjectUtilTest()
    {
        super();
    }


    /**
     *
     */
    @Test
    public void morph()
    {
        Dimension dimension = ObjectUtil.morph("200,100", Dimension.class);
        assertEquals(200, dimension.width);
        assertEquals(100, dimension.height);
    }
}