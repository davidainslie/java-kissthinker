package com.kissthinker.core.collection.set;

import java.util.Collections;
import java.util.HashSet;

/**
 * @author David Ainslie
 *
 */
public final class SetUtil
{
    /**
     *
     * @param <O>
     * @return
     */
    public static <O> HashSet<O> hashSet()
    {
        return new HashSet<>();
    }

    /**
     *
     * @param <O>
     * @param objects
     * @return
     */
    @SafeVarargs
    public static <O> HashSet<O> hashSet(O... objects)
    {
        HashSet<O> hashSet = hashSet();

        if (objects != null)
        {
            Collections.addAll(hashSet, objects);
        }

        return hashSet;
    }

    /**
     * Utiltity.
     */
    private SetUtil()
    {
        super();
    }
}