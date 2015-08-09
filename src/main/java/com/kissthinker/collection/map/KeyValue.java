package com.kissthinker.collection.map;

import java.io.Serializable;

/**
 * Immutable generic "key value" pair.
 * <br/>
 * @param <K> key
 * @param <V> value
 * @author David Ainslie
 *
 */
public final class KeyValue<K, V> implements Comparable<KeyValue<K, V>>,
                                             Serializable
{
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    private final K key;

    /** */
    private final V value;

    /**
     *
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @return KeyValue<K, V>
     */
    @SuppressWarnings("unchecked")
    public static <K, V> KeyValue<K, V> keyValue(K key, V value)
    {
        return new KeyValue(key, value);
    }

    /**
     * @param key the key
     * @param value the value
     */
    public KeyValue(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    /**
     * Getter
     * @return key
     */
    public K key()
    {
        return key;
    }

    /**
     * Getter
     * @return value
     */
    public V value()
    {
        return value;
    }

    /**
     * @param other to compare to
     * @return int of comparison
     */
    @Override
    public int compareTo(KeyValue<K, V> other)
    {
        if (key.equals(other))
        {
            return 0;
        }
        else
        {
            int compareTo = key.toString().compareTo(other.key.toString());

            if (compareTo == 0)
            {
                return value.toString().compareTo(other.value.toString());
            }
            else
            {
                return compareTo;
            }
        }
    }

    /**
     * @see java.lang.Object#hashCode()
     * @return int
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * @param otherObject to equal to
     * @return true/false
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object otherObject)
    {
        if (otherObject == null)
        {
            return false;
        }

        KeyValue<K, V> other = (KeyValue<K, V>)otherObject;

        if (key.equals(other.key))
        {
            if (value.equals(other.value))
            {
                return true;
            }
        }

        return false;
    }
}