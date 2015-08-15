package com.kissthinker.object;

import com.kissthinker.morph.Morpher;
import com.kissthinker.morph.ezmorph.EZMorpher;

/**
 * @author David Ainslie
 *
 */
@Singleton
public class ObjectUtil
{
    /** */
    private static final Morpher MORPHER = new EZMorpher();

    /**
     * Morph (transform/convert) given object to the required class i.e to an object of type classToMorphTo, Class<O>
     * @param object
     * @param toClass
     * @return O the morphed object or null if the given object could not be morphed.
     */
    @SuppressWarnings("all")
    public static <O> O morph(Object object, Class<O> toClass)
    {
        return (O)MORPHER.morph(object, toClass);
    }

    /**
     * Utility.
     */
    private ObjectUtil()
    {
        super();
    }
}