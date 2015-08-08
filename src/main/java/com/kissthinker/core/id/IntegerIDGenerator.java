package com.kissthinker.core.id;

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
     * @see com.kissthinker.core.id.IDGenerator#next()
     */
    @Override
    public Integer next()
    {
        return atomicInteger.incrementAndGet();
    }
}