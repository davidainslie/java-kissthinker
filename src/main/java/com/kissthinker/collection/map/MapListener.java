package com.kissthinker.collection.map;

/**
 * @author David Ainslie
 *
 */
public interface MapListener<K, V>
{
    /**
     * 
     * @param key Key
     * @param value Value
     */
    void onPut(K key, V value);

    /**
     * 
     * @param key Key
     * @param value Value
     */
    void onRemove(K key, V value);
}