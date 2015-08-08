package com.kissthinker.core.id;

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
     * @see com.kissthinker.core.id.IDGenerator#next()
     */
    @Override
    public UUID next()
    {
        return UUID.randomUUID();
    }
}