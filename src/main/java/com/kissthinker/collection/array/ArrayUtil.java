package com.kissthinker.collection.array;

import java.util.Arrays;
import com.kissthinker.object.Singleton;

/**
 * @author David Ainslie
 *
 */
@Singleton
public final class ArrayUtil
{
    /**
     * Essentially a conversion from varargs to array
     * @param args Zero to many objects
     * @return Object[]
     */
    public static Object[] va(Object... args)
    {
        return args;
    }

    /**
     *
     * @param <O> Object type
     * @param array Array of objects
     * @param object Object
     * @return true if given object is in the given array
     */
    public static <O> boolean contains(O[] array, O object)
    {
        if (array != null && array.length > 0)
        {
            for (O o : array)
            {
                if (o.equals(object))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     *
     * @param <I> Input type
     * @param <O> Output type
     * @param input Array of input
     * @param outputArrayClass Class of output
     * @return O[]
     */
    public static <I, O> O[] convert(I[] input, Class<O[]> outputArrayClass)
    {
        return Arrays.copyOf(input, input.length, outputArrayClass);
    }

    /**
     * Utility.
     */
    private ArrayUtil()
    {
        super();
    }
}