package com.kissthinker.collection.map;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * A {@link LinkedHashMap} that acts as a "least recently used map".
 *
 * When adding ({@link #put(Object, Object)}) to the map, upon exceeding the maximum number of entries, the "oldest" entry is removed.
 * @author David Ainslie
 * @param <K> Key type
 * @param <V> Value type
 */
public class LeastRecentlyUsedMap<K, V> extends LinkedHashMap<K, V>
{
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    private static final float LOAD_FACTOR = 0.75f;

    /** */
    private static final int DEFAULT_MAXIMUM_NUMBER_OF_ENTRIES = 30;

    /** */
    private int maximumNumberOfEntries;

    /**
     *
     * Default to {@value #DEFAULT_MAXIMUM_NUMBER_OF_ENTRIES} maximum number of entries
     */
    public LeastRecentlyUsedMap()
    {
        super(DEFAULT_MAXIMUM_NUMBER_OF_ENTRIES);
    }

    /**
     *
     * @param maximumNumberOfEntries Maximum number of entries
     */
    public LeastRecentlyUsedMap(int maximumNumberOfEntries)
    {
        super((int)Math.ceil(maximumNumberOfEntries / LOAD_FACTOR) + 1, LOAD_FACTOR, true);
        this.maximumNumberOfEntries = maximumNumberOfEntries;
    }

    /**
     *
     * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
     */
    @Override
    protected boolean removeEldestEntry(Entry<K, V> eldest)
    {
        return size() > maximumNumberOfEntries;
    }
}