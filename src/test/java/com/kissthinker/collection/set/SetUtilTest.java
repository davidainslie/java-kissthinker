package com.kissthinker.collection.set;

import java.util.Set;
import org.junit.Test;
import static com.kissthinker.collection.set.SetUtil.hashSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author David Ainslie
 *
 */
public class SetUtilTest
{
    /** */
    public SetUtilTest()
    {
        super();
    }

    /** */
    @Test
    public void hashSetNew()
    {
        Set<String> strings = hashSet();
        assertTrue(strings.isEmpty());
    }

    /**
     *
     */
    @Test
    public void hashSetInitialised()
    {
        Set<String> strings = hashSet("Me", "You");
        assertEquals(2, strings.size());
    }
}