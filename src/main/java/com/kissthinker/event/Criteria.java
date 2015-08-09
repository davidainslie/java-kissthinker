package com.kissthinker.event;

/**
 * @author David Ainslie
 *
 */
@Deprecated
public class Criteria<E>
{
    /**
     *
     */
    public Criteria()
    {
        super();
    }

    /**
     *
     * @param event
     * @return
     */
    public boolean check(E event)
    {
        return true;
    }
}