package com.kissthinker.id;

import java.util.UUID;

/**
 * @author David Ainslie
 *
 */
public class UUIDGenerator implements IDGenerator<UUID>
{
    /**
     *
     */
    public UUIDGenerator()
    {
        super();
    }

    /**
     *
     * @see com.kissthinker.id.IDGenerator#next()
     */
    @Override
    public UUID next()
    {
        return UUID.randomUUID();
    }
}