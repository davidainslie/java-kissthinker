package com.kissthinker.core.collection.map;

import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.fail;

/**
 *
 * @author David Ainslie
 *
 */
public class LeastRecentlyUsedMapTest
{
    /** */
    @Test
    public void threeMaximumEntries()
    {
        LeastRecentlyUsedMap<String, String> map = new LeastRecentlyUsedMap<>(3);

        map.put("1", "one"); // 1
        map.put("2", "two"); // 2 1
        map.put("3", "three"); // 3 2 1
        map.put("4", "four"); // 4 3 2

        if (map.containsKey("1"))
        {
            fail();
        }

        if (!map.containsKey("2"))
        {
            fail();
        }

        map.put("5", "five"); // 5 4 3
        map.put("4", "second four"); // 5 4 3

        // Verify content.
        if (map.size() != 3)
        {
            fail();
        }

        if (!map.get("5").equals("five"))
        {
            fail();
        }

        if (!map.get("4").equals("second four"))
        {
            fail();
        }

        if (!map.get("3").equals("three"))
        {
            fail();
        }

        // List content (not really unit test as no asserting).
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}