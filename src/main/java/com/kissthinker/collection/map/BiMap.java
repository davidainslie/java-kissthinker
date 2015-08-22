package com.kissthinker.collection.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A "bi map" or "two way map".
 * <p>
 * Get a value with a given key (as in a traditional map), but also get a key with a given value.
 * @author David Ainslie
 *
 */
public class BiMap<K, V> implements Map<K, V>
{
    /** */
    private final Map<K, V> keyValueMap = new HashMap<>();

    /** */
    private final Map<V, K> valueKeyMap = new HashMap<>();

    /** */
    public BiMap()
    {
        super();
    }

    /**
     *
     * @param key Key
     * @param value Value
     */
    @Override
    public V put(K key, V value)
    {
        valueKeyMap.put(value, key);
        return keyValueMap.put(key, value);
    }

    /**
     *
     * @param key Key
     * @return V or null
     */
    public V value(K key)
    {
        return keyValueMap.get(key);
    }

    /**
     *
     * @param value Value
     * @return true if contained
     */
    public boolean hasValue(V value)
    {
        return valueKeyMap.containsKey(value);
    }

    /**
     *
     * @param key Key
     * @return V or null
     */
    public V removeValue(K key)
    {
        V value = keyValueMap.remove(key);
        valueKeyMap.remove(value);
        
        return value;
    }

    /**
     *
     * @param value Value
     * @return K or null
     */
    public K key(V value)
    {
        return valueKeyMap.get(value);
    }

    /**
     *
     * @param key Key
     * @return true if contained
     */
    public boolean hasKey(K key)
    {
        return keyValueMap.containsKey(key);
    }

    /**
     *
     * @param value Value
     * @return K or null
     */
    public K removeKey(V value)
    {
        K key = valueKeyMap.remove(value);
        keyValueMap.remove(key);
        
        return key;
    }

    /** */
    @Override
    public int size()
    {
        return keyValueMap.size();
    }

    /** */
    @Override
    public boolean isEmpty()
    {
        return keyValueMap.isEmpty();
    }

    /**
     * 
     * @param key Key
     * @return Value
     */
    @Override
    public V get(Object key)
    {
        return keyValueMap.get(key);
    }

    /**
     * 
     * @param key Key
     * @return Value
     */
    @Override
    public V remove(Object key)
    {
        V value = keyValueMap.remove(key);
        valueKeyMap.remove(value);
        
        return value;
    }

    /**
     * 
     * @param m Map
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m)
    {
        keyValueMap.putAll(m);
        // TODO need to update valueKeyMap
    }

    /** */
    @Override
    public void clear()
    {
        keyValueMap.clear();
        valueKeyMap.clear();
    }

    /** */
    @Override
    public Set<K> keySet()
    {
        return keyValueMap.keySet();
    }

    /** */
    @Override
    public Collection<V> values()
    {
        return keyValueMap.values();
    }

    /** */
    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet()
    {
        return keyValueMap.entrySet();
    }

    /**
     * 
     * @param key Key
     * @return True or false
     */
    @Override
    public boolean containsKey(Object key)
    {
        return keyValueMap.containsKey(key);
    }

    /**
     * 
     * @param value Value
     * @return True or false
     */
    @Override
    public boolean containsValue(Object value)
    {
        return valueKeyMap.containsKey(value);
    }
}