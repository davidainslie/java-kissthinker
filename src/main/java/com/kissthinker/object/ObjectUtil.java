package com.kissthinker.object;

import com.kissthinker.configure.Configurable;
import com.kissthinker.configure.Configure;
import com.kissthinker.morph.Morpher;

/**
 * @author David Ainslie
 *
 */
@Singleton
@Configurable
public class ObjectUtil
{
    /** */
    @Configure
    private static final Morpher MORPHER = null;

    /**
     * Morph (transform/convert) given object to the required class i.e to an object of type classToMorphTo, Class
     * @param object Object to morph
     * @param toClass Class to morph object to
     * @param <O> Object type
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