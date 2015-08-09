package com.kissthinker.collection.map;

/**
 * @author David Ainslie
 *
 */
public interface MapListener<K, V>
{
    /**
     * 
     * @param key
     * @param value
     */
    void onPut(K key, V value);

    /**
     * 
     * @param key
     * @param value
     */
    void onRemove(K key, V value);
}