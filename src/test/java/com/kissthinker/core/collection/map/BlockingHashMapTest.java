package com.kissthinker.core.collection.map;


import static com.kissthinker.core.collection.map.MapUtil.blockingHashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;

import com.kissthinker.core.concurrency.ExecutorUtil;


/**
 * @author David Ainslie
 *
 */
public class BlockingHashMapTest
{
    /** */
    private BlockingHashMap<String, String> blockingHashMap;


    /**
     *
     */
    public BlockingHashMapTest()
    {
        super();
    }


    /**
     *
     */
    @Before
    public void initialise()
    {
        blockingHashMap = blockingHashMap();
    }


    /**
     *
     */
    @Test
    public void get()
    {
        final String key = "key";
        final String value = "value";

        final AtomicBoolean putExecuted = new AtomicBoolean(false);

        ExecutorUtil.schedule(2, TimeUnit.SECONDS, new Runnable()
        {
            /**
             *
             * @see java.lang.Runnable#run()
             */
            @Override
            public void run()
            {
                putExecuted.set(true);
                blockingHashMap.put(key, value);
            }
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

        ExecutorUtil.schedule(2, TimeUnit.SECONDS, new Runnable()
        {
            /**
             *
             * @see java.lang.Runnable#run()
             */
            @Override
            public void run()
            {
                putExecuted.set(true);
                blockingHashMap.put("keyDifferent", value);
            }
        });

        String gotValue = blockingHashMap.get(key, 4, TimeUnit.SECONDS);
        assertNull(gotValue);
        assertTrue(putExecuted.get());
    }
}