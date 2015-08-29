package com.kissthinker.id;

/**
 * @author David Ainslie
 *
 */
public interface IDGenerator<I>
{
    /**
     *
     * @return ID
     */
    I next();
}