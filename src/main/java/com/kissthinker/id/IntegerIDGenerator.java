package com.kissthinker.id;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author David Ainslie
 *
 */
public class IntegerIDGenerator implements IDGenerator<Integer>
{
    /** */
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    /** */
    public IntegerIDGenerator()
    {
        super();
    }

    /**
     *
     * @see com.kissthinker.id.IDGenerator#next()
     */
    @Override
    public Integer next()
    {
        return atomicInteger.incrementAndGet();
    }
}