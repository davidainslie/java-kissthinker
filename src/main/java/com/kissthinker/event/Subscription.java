package com.kissthinker.event;

import com.kissthinker.function.Function;

/**
 * @author David Ainslie
 *
 */
public class Subscription<S, E>
{
    /** */
    private S subject;

    /** */
    private final Function<?> callback;

    /** */
    private final Function<Boolean> criteria;

    /**
     *
     * @param subject
     * @param callback
     */
    public Subscription(S subject, Function<?> callback)
    {
        this(subject, callback, null);
    }

    /**
     *
     * @param subject
     * @param callback
     * @param criteria
     */
    public Subscription(S subject, Function<?> callback, Function<Boolean> criteria)
    {
        super();
        this.subject = subject;
        this.callback = callback;
        this.criteria = criteria;
    }

    /**
     * Getter.
     * @return the subject
     */
    public S subject()
    {
        return subject;
    }

    /**
     * Getter.
     * @return the callback function
     */
    public Function<?> callback()
    {
        return callback;
    }

    /**
     * Getter.
     * @return the criteria function
     */
    public Function<Boolean> criteria()
    {
        return criteria;
    }
}