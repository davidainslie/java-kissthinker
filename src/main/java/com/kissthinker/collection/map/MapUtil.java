package com.kissthinker.collection.map;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import com.kissthinker.object.Singleton;

/**
 * @author David Ainslie
 *
 */
@Singleton
public final class MapUtil
{
    /**
     *
     * @param <K> Key type
     * @param <V> Value type
     * @return FluentHashMap
     */
    public static <K, V> FluentHashMap<K, V> hashMap()
    {
        return new FluentHashMap<>();
    }

    /**
     *
     * @param <K> Key type
     * @param <V> Value type
     * @param keyValues Keys and their values
     * @return FluentHashMap
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
     * @param <K> Key type
     * @param <V> Value type
     * @param keyValues Keys and their values
     * @return FluentHashMap
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
     * @param <K> Key type
     * @param <V> Value type
     * @return ConcurrentHashMap
     */
    public static <K, V> ConcurrentHashMap<K, V> concurrentHashMap()
    {
        return new ConcurrentHashMap<>();
    }

    /**
     *
     * @param <K> Key type
     * @param <V> Value type
     * @return BlockingHashMap
     */
    public static <K, V> BlockingHashMap<K, V> blockingHashMap()
    {
        return new BlockingHashMap<>();
    }

    /**
     *
     * @param <K> Key type
     * @param <V> Value type
     * @return BiMap
     */
    public static <K, V> BiMap<K, V> biMap()
    {
        return new BiMap<>();
    }

    /**
     *
     * @param <K> Key type
     * @param <V> Value type
     * @return LeastRecentlyUsedMap
     */
    public static <K, V> LeastRecentlyUsedMap<K, V> lruMap()
    {
        return new LeastRecentlyUsedMap<>();
    }

    /**
     *
     * @param <K> Key type
     * @param <V> Value type
     * @return WeakHashMap
     */
    public static <K, V> WeakHashMap<K, V> weakHashMap()
    {
        return new WeakHashMap<>();
    }

    /**
     *
     * @param <K> Key type
     * @param <V> Value type
     * @return IdentityHashMap
     */
    public static <K, V> IdentityHashMap<K, V> identityHashMap()
    {
        return new IdentityHashMap<>();
    }

    /**
     *
     * @param <K> Key type
     * @param <V> Value type
     * @return IdentityWeakHashMap
     */
    public static <K, V> IdentityWeakHashMap<K, V> identityWeakHashMap()
    {
        return new IdentityWeakHashMap<>();
    }

    /**
     *
     * @param <K> Key type
     * @param <V> Value type
     * @param key Key
     * @param value Value
     * @return KeyValue
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
     * @param <K> Key type
     * @param <V> Value type
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
         * @param initialCapacity Initial capacity
         * @param loadFactor Load factor
         */
        public FluentHashMap(int initialCapacity, float loadFactor)
        {
            super(initialCapacity, loadFactor);
        }

        /**
         * @param initialCapacity Initial capacity
         */
        public FluentHashMap(int initialCapacity)
        {
            super(initialCapacity);
        }

        /**
         * @param m Map
         */
        public FluentHashMap(Map<? extends K, ? extends V> m)
        {
            super(m);
        }

        /**
         *
         * @param key Key
         * @param value Value
         * @return Map
         */
        public FluentHashMap<K, V> keyValue(K key, V value)
        {
            put(key, value);
            return this;
        }
    }
}