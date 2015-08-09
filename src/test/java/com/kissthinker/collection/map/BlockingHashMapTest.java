package com.kissthinker.collection.map;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Before;
import org.junit.Test;
import com.kissthinker.concurrency.ExecutorUtil;
import static com.kissthinker.collection.map.MapUtil.blockingHashMap;
import static org.junit.Assert.*;

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

        final AtomicBoolean putExecuted = new AtomicBoolean(false);

        ExecutorUtil.schedule(2, TimeUnit.SECONDS, () -> {
            blockingHashMap.put(key, value);
            putExecuted.set(true);
        });

        String gotValue = blockingHashMap.get(key);
        assertEquals(value, gotValue);
        assertTrue(putExecuted.get());
    }

    /**
     *
     */
    @Test
    public void getWithTimeOut()
    {
        final String key = "key";
        final String value = "value";

        final AtomicBoolean putExecuted = new AtomicBoolean(false);

        ExecutorUtil.schedule(2, TimeUnit.SECONDS, () -> {
            blockingHashMap.put("keyDifferent", value);
            putExecuted.set(true);
        });

        String gotValue = blockingHashMap.get(key, 4, TimeUnit.SECONDS);
        assertNull(gotValue);
        assertTrue(putExecuted.get());
    }
}