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
     * @return ArrayList
     */
    public static <O> ArrayList<O> arrayList()
    {
        return new ArrayList<>();
    }

    /**
     *
     * @param objects Objects
     * @param <O> Type of objects in array
     * @return ArrayList
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
     * @return LinkedList
     */
    public static <O> LinkedList<O> linkedList()
    {
        return new LinkedList<>();
    }

    /**
     *
     * @param objects Objects
     * @param <O> Type of objects in list
     * @return LinkedList
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