package com.kissthinker.collection.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import com.kissthinker.object.Singleton;

/**
 * @author David Ainslie
 *
 */
@Singleton
public final class ListUtil
{
    /**
     *
     * @param <O> Type of objects in array
     * @return ArrayList<O>
     */
    public static <O> ArrayList<O> arrayList()
    {
        return new ArrayList<>();
    }

    /**
     *
     * @param <O> Type of objects in array
     * @return ArrayList<O>
     */
    @SafeVarargs
    public static <O> ArrayList<O> arrayList(O... objects)
    {
        ArrayList<O> arrayList = arrayList();

        if (objects != null)
        {
            Collections.addAll(arrayList, objects);
        }

        return arrayList;
    }

    /**
     *
     * @param <O> Type of objects in list
     * @return LinkedList<O>
     */
    public static <O> LinkedList<O> linkedList()
    {
        return new LinkedList<>();
    }

    /**
     *
     * @param <O> Type of objects in list
     * @return LinkedList<O>
     */
    @SafeVarargs
    public static <O> LinkedList<O> linkedList(O... objects)
    {
        LinkedList<O> linkedList = linkedList();

        if (objects != null)
        {
            Collections.addAll(linkedList, objects);
        }

        return linkedList;
    }
   
    /**
     * Utility
     */
    private ListUtil()
    {
        super();
    }
}