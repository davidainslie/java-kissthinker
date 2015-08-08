package com.kissthinker.core.coder;

/**
 * ID wrapper around an object.
 * <br/>
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
     * @param id
     * @param object
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