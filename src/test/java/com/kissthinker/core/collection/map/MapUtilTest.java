package com.kissthinker.core.collection.map;


import static com.kissthinker.core.collection.map.MapUtil.hashMap;
import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;


/**
 * @author David Ainslie
 *
 */
public class MapUtilTest
{
    /**
     *
     */
    public MapUtilTest()
    {
        super();
    }


    /**
     *
     */
    @Test
    public void hashMapNew()
    {
        Map<Integer, String> strings = hashMap();
        strings.put(1, "Me");
        strings.put(2, "You");

        assertEquals("You", strings.get(2));
    }


    /**
     * In this case, we cannot call just "hashMap()".<br/>
     * To avoid IDE complaints regarding generics, we must call "MapUtil.<Integer, String>hashMap()". Yes? Annoying!
     */
    @Test
    public void hashMapInitialised()
    {
        Map<Integer, String> strings = MapUtil.<Integer, String>hashMap().keyValue(1, "Me").keyValue(2, "You");
        // Is this really better than calling strings.put(..)?

        assertEquals("You", strings.get(2));
    }
}