package com.kissthinker.collection.set;

import java.util.Collections;
import java.util.HashSet;
import com.kissthinker.object.Singleton;

/**
 * @author David Ainslie
 *
 */
@Singleton
public final class SetUtil
{
    /**
     *
     * @param <O> Object type
     * @return HashSet
     */
    public static <O> HashSet<O> hashSet()
    {
        return new HashSet<>();
    }

    /**
     *
     * @param <O> Object type
     * @param objects Zero to many objects
     * @return HashSet of given objects
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