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
     * @param supportsClass Supports class
     * @param morphsToClass Morphs to class
     */
    public AbstractObjectMorpher(final Class<?> supportsClass, final Class<?> morphsToClass)
    {
        super();
        this.supportsClass = supportsClass;
        this.morphsToClass = morphsToClass;
    }

    /** */
    @Override
    public boolean supports(@SuppressWarnings("rawtypes") Class class_)
    {
        return supportsClass.isAssignableFrom(class_);
    }

    /** */
    @Override
    public Class<?> morphsTo()
    {
        return morphsToClass;
    }
}