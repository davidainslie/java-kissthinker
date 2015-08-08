package com.kissthinker.core.object;

import com.kissthinker.core.configure.Configurable;
import com.kissthinker.core.configure.Configure;
import com.kissthinker.core.morph.Morpher;

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
     * Morph (transform/convert) given object to the required class i.e to an object of type classToMorphTo, Class<O>
     * @param object
     * @param toClass
     * @return O the morphed object or null if the given object could not be morphed.
     */
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