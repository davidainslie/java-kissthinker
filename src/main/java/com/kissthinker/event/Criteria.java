package com.kissthinker.event;

/**
 * @author David Ainslie
 *
 */
@Deprecated
public class Criteria<E>
{
    /** */
    public Criteria()
    {
        super();
    }

    /**
     *
     * @param event Event
     * @return True or false
     */
    public boolean check(E event)
    {
        return true;
    }
}