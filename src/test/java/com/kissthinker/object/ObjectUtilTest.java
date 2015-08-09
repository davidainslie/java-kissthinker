package com.kissthinker.object;

import java.awt.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * @author David Ainslie
 *
 */
public class ObjectUtilTest
{
    /** */
    public ObjectUtilTest()
    {
        super();
    }

    /** */
    @Test
    public void morph()
    {
        Dimension dimension = ObjectUtil.morph("200,100", Dimension.class);
        assertEquals(200, dimension.width);
        assertEquals(100, dimension.height);
    }
}