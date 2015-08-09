package com.kissthinker.collection.array;

import java.util.Arrays;

/**
 * @author David Ainslie
 *
 */
public final class ArrayUtil
{
    /**
     * Essentially a conversion from varargs to array
     * @param args
     * @return Object[]
     */
    public static Object[] va(Object... args)
    {
        return args;
    }

    /**
     *
     * @param <O>
     * @param array
     * @param object
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
     * @param <I>
     * @param <O>
     * @param input
     * @param outputArrayClass
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