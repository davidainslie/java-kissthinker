package com.kissthinker.collection;

import java.util.ArrayList;
import java.util.Collection;
import com.kissthinker.object.Singleton;

/**
 * @author David Ainslie
 *
 */
@Singleton
public final class CollectionUtil
{
    /**
     *
     * @param collection Collection
     * @return String representing given collection
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
     * @param <O> Object type
     * @param collection Collection
     * @return Collection
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