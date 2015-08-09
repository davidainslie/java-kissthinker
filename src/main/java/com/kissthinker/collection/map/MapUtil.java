package com.kissthinker.collection.map;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author David Ainslie
 *
 */
public final class MapUtil
{
    /**
     *
     * @param <K>
     * @param <V>
     * @return FluentHashMap<K, V>
     */
    public static <K, V> FluentHashMap<K, V> hashMap()
    {
        return new FluentHashMap<>();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param keyValues
     * @return FluentHashMap<K, V>
     */
    @SafeVarargs
    public static <K, V> FluentHashMap<K, V> hashMap(KeyValue<K, V>... keyValues)
    {
        FluentHashMap<K, V> hashMap = hashMap();

        for (KeyValue<K, V> keyValue : keyValues)
        {
            hashMap.put(keyValue.key(), keyValue.value());
        }

        return hashMap;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param keyValues
     * @return FluentHashMap<K, V>
     */
    public static <K, V> FluentHashMap<K, V> hashMap(KeyValue<K, V>[][] keyValues)
    {
        FluentHashMap<K, V> hashMap = hashMap();

        for (KeyValue<K, V>[] keyValue : keyValues)
        {
            for (KeyValue<K, V> keyValueNested : keyValue)
            {
                hashMap.put(keyValueNested.key(), keyValueNested.value());
            }
        }

        return hashMap;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @return ConcurrentHashMap<K, V>
     */
    public static <K, V> ConcurrentHashMap<K, V> concurrentHashMap()
    {
        return new ConcurrentHashMap<>();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @return BlockingHashMap<K, V>
     */
    public static <K, V> BlockingHashMap<K, V> blockingHashMap()
    {
        return new BlockingHashMap<>();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @return BiMap<K, V>
     */
    public static <K, V> BiMap<K, V> biMap()
    {
        return new BiMap<>();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @return LeastRecentlyUsedMap<K, V>
     */
    public static <K, V> LeastRecentlyUsedMap<K, V> lruMap()
    {
        return new LeastRecentlyUsedMap<>();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @return WeakHashMap<K, V>
     */
    public static <K, V> WeakHashMap<K, V> weakHashMap()
    {
        return new WeakHashMap<>();
    }

    /**
     * 
     * @return
     */
    public static <K, V> IdentityHashMap<K, V> identityHashMap()
    {
        return new IdentityHashMap<>();
    }
    
    /**
     * 
     * @return
     */
    public static <K, V> IdentityWeakHashMap<K, V> identityWeakHashMap()
    {
        return new IdentityWeakHashMap<>();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @return
     */
    public static <K, V> KeyValue<K, V> keyValue(K key, V value)
    {
        return new KeyValue<>(key, value);
    }

    /**
     * Utility
     */
    private MapUtil()
    {
        super();
    }

    /**
     *
     * @author David Ainslie
     *
     * @param <K>
     * @param <V>
     */
    public static class FluentHashMap<K, V> extends HashMap<K, V>
    {
        /** */
        private static final long serialVersionUID = 1L;

        /** */
        public FluentHashMap()
        {
            super();
        }

        /**
         * @param initialCapacity
         * @param loadFactor
         */
        public FluentHashMap(int initialCapacity, float loadFactor)
        {
            super(initialCapacity, loadFactor);
        }

        /**
         * @param initialCapacity
         */
        public FluentHashMap(int initialCapacity)
        {
            super(initialCapacity);
        }

        /**
         * @param m
         */
        public FluentHashMap(Map<? extends K, ? extends V> m)
        {
            super(m);
        }

        /**
         *
         * @param key
         * @param value
         * @return
         */
        public FluentHashMap<K, V> keyValue(K key, V value)
        {
            put(key, value);
            return this;
        }
    }
}