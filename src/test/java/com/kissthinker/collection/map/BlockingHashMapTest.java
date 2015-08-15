package com.kissthinker.collection.map;

import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import com.kissthinker.concurrency.ExecutorUtil;
import static com.kissthinker.collection.map.MapUtil.blockingHashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author David Ainslie
 *
 */
public class BlockingHashMapTest
{
    /** */
    private BlockingHashMap<String, String> blockingHashMap;

    /** */
    public BlockingHashMapTest()
    {
        super();
    }

    /** */
    @Before
    public void initialise()
    {
        blockingHashMap = blockingHashMap();
    }

    /** */
    @Test
    public void get()
    {
        final String key = "key";
        final String value = "value";

        ExecutorUtil.schedule(1, TimeUnit.SECONDS, () -> blockingHashMap.put(key, value));

        String gotValue = blockingHashMap.get(key);
        assertEquals(value, gotValue);
    }

    /**
     *
     */
    @Test
    public void getWithTimeOut()
    {
        final String key = "key";
        final String value = "value";

        ExecutorUtil.schedule(1, TimeUnit.SECONDS, () -> blockingHashMap.put("wrongKey", value));

        String gotValue = blockingHashMap.get(key, 2, TimeUnit.SECONDS);
        assertNull(gotValue);
    }
}