package com.kissthinker.core.collection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author David Ainslie
 *
 */
public final class CollectionUtil
{
    /**
     *
     * @param collection
     * @return
     */
    public static String toString(Collection<?> collection)
    {
        if (collection == null || collection.isEmpty())
        {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        for (Object object : collection)
        {
            if (object instanceof Collection<?>)
            {
                stringBuilder.append(toString((Collection<?>)object));
            }
            else
            {
                stringBuilder.append(object).append(", ");
            }
        }

        stringBuilder.append("]");

        return stringBuilder.toString().replaceAll(", ]", "]");
    }

    /**
     *
     * @param <O>
     * @param collection
     * @return Collection<O>
     */
    public static <O> Collection<O> newInstance(Collection<O> collection)
    {
        try
        {
            if (collection.getClass().getName().endsWith("$ArrayList"))
            {
                return new ArrayList<>();
            }

            @SuppressWarnings("unchecked")
            Collection<O> newCollection = collection.getClass().newInstance();

            return newCollection;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Utility.
     */
    private CollectionUtil()
    {
    }
}