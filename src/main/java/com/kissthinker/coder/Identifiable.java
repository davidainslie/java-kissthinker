package com.kissthinker.coder;

/**
 * ID wrapper around an object.
 *
 * @author David Ainslie
 *
 */
public class Identifiable<I, O>
{
    /** */
    private final I id;

    /** */
    private final O object;

    /**
     *
     * @param id ID
     * @param object Object
     */
    public Identifiable(I id, O object)
    {
        super();
        this.id = id;
        this.object = object;
    }

    /**
     * @return the id
     */
    public I id()
    {
        return id;
    }

    /**
     * @return the object
     */
    public O object()
    {
        return object;
    }
}