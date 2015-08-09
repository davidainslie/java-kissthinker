package com.kissthinker.morph.ezmorph;

import net.sf.ezmorph.ObjectMorpher;

/**
 * @author David Ainslie
 *
 */
public abstract class AbstractObjectMorpher implements ObjectMorpher
{
    /** */
    private final Class<?> supportsClass;

    /** */
    private final Class<?> morphsToClass;

    /**
     *
     * @param supportsClass
     * @param morphsToClass
     */
    public AbstractObjectMorpher(final Class<?> supportsClass, final Class<?> morphsToClass)
    {
        super();
        this.supportsClass = supportsClass;
        this.morphsToClass = morphsToClass;
    }

    /**
     *
     */
    @Override
    public boolean supports(@SuppressWarnings("rawtypes") Class class_)
    {
        if (supportsClass.isAssignableFrom(class_))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     *
     */
    @Override
    public Class<?> morphsTo()
    {
        return morphsToClass;
    }
}