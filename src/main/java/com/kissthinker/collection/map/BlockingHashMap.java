package com.kissthinker.collection.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.kissthinker.collection.map.MapUtil.concurrentHashMap;

/**
 * @author David Ainslie
 *
 */
public class BlockingHashMap<K, V> implements Map<K, V>
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockingHashMap.class);

    /** */
    private final ConcurrentMap<K, SynchronousQueue<V>> delegateConcurrentMap = concurrentHashMap();

    /**
     *
     */
    public BlockingHashMap()
    {
        super();
    }

    /**
     * @param initialCapacity
     * @param loadFactor
     * @param concurrencyLevel
     */
    public BlockingHashMap(int initialCapacity, float loadFactor, int concurrencyLevel)
    {
        // TODO
        //super(initialCapacity, loadFactor, concurrencyLevel);
    }

    /**
     * @param initialCapacity
     * @param loadFactor
     */
    public BlockingHashMap(int initialCapacity, float loadFactor)
    {
        // TODO
        //super(initialCapacity, loadFactor);
    }

    /**
     * @param initialCapacity
     */
    public BlockingHashMap(int initialCapacity)
    {
        // TODO
        //super(initialCapacity);
    }

    /**
     *
     * @see java.util.Map#clear()
     */
    @Override
    public void clear()
    {
        // TODO Auto-generated method stub
        delegateConcurrentMap.clear();
    }

    /**
     *
     * @see java.util.Map#containsKey(java.lang.Object)
     */
    @Override
    public boolean containsKey(Object key)
    {
        // TODO Auto-generated method stub
        return delegateConcurrentMap.containsKey(key);
    }

    /**
     *
     * @see java.util.Map#containsValue(java.lang.Object)
     */
    @Override
    public boolean containsValue(Object value)
    {
        // TODO Auto-generated method stub
        return delegateConcurrentMap.containsValue(value);
    }

    /**
     *
     * @see java.util.Map#entrySet()
     */
    @Override
    public Set<Entry<K, V>> entrySet()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * A blocking version of {@link Map#get(Object)}
     * @see java.util.Map#get(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public V get(Object key)
    {
        LOGGER.trace("Class of given key = {}, toString of given key = {}", key.getClass(), key);

        try
        {
            delegateConcurrentMap.putIfAbsent((K)key, new SynchronousQueue<V>());

            SynchronousQueue<V> synchronousQueue = delegateConcurrentMap.get(key);
            return synchronousQueue.take();
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * A blocking version of {@link Map#get(Object)} but with a timeout
     * @param key
     * @param timeOut
     * @param timeUnit
     * @return V
     */
    @SuppressWarnings("unchecked")
    public V get(Object key, long timeOut, TimeUnit timeUnit)
    {
        try
        {
            delegateConcurrentMap.putIfAbsent((K)key, new SynchronousQueue<V>());

            SynchronousQueue<V> synchronousQueue = delegateConcurrentMap.get(key);
            return synchronousQueue.poll(timeOut, timeUnit);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @see java.util.Map#isEmpty()
     */
    @Override
    public boolean isEmpty()
    {
        // TODO Auto-generated method stub
        return delegateConcurrentMap.isEmpty();
    }

    /**
     *
     * @see java.util.Map#keySet()
     */
    @Override
    public Set<K> keySet()
    {
        // TODO Auto-generated method stub
        return delegateConcurrentMap.keySet();
    }

    /**
     * A non blocking version of "put" (which is the default). For blocking see {@link #put(Object, Object, boolean)}
     * A slight difference to the usual implementation of {@link Map#put(Object, Object)} in that the given value is return upon success, and a null upon failure.
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public V put(K key, V value)
    {
        LOGGER.trace("Class of given key = {}, toString of given key = {}", key.getClass(), key);

        delegateConcurrentMap.putIfAbsent(key, new SynchronousQueue<V>());
        SynchronousQueue<V> synchronousQueue = delegateConcurrentMap.get(key);

        if (synchronousQueue.offer(value))
        {
            return value;
        }

        return null;
    }

    /**
     * Either a blocking or non-blocking version of "put".
     * @param key
     * @param value
     * @param blocking
     * @return
     */
    public V put(K key, V value, boolean blocking)
    {
        if (blocking)
        {
            LOGGER.trace("Class of given key = {}, toString of given key = {}", key.getClass(), key);

            delegateConcurrentMap.putIfAbsent(key, new SynchronousQueue<V>());
            SynchronousQueue<V> synchronousQueue = delegateConcurrentMap.get(key);

            try
            {
                synchronousQueue.put(value);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return value;
        }
        
        return put(key, value);
    }
    
    /**
     * Either a blocking (with a time out) or non-blocking version of "put".
     * @param key
     * @param value
     * @param blocking
     * @param timeOut
     * @param timeUnit
     * @return
     */
    public V put(K key, V value, boolean blocking, long timeOut, TimeUnit timeUnit)
    {
        if (blocking)
        {
            LOGGER.trace("Class of given key = {}, toString of given key = {}", key.getClass(), key);

            delegateConcurrentMap.putIfAbsent(key, new SynchronousQueue<V>());
            SynchronousQueue<V> synchronousQueue = delegateConcurrentMap.get(key);

            try
            {
                synchronousQueue.offer(value, timeOut, timeUnit);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return value;
        }
        
        return put(key, value);
    }

    /**
     *
     * @see java.util.Map#putAll(java.util.Map)
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m)
    {
        // TODO Auto-generated method stub
    }

    /**
     *
     * @see java.util.Map#remove(java.lang.Object)
     */
    @Override
    public V remove(Object key)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @see java.util.Map#size()
     */
    @Override
    public int size()
    {
        // TODO Auto-generated method stub
        return delegateConcurrentMap.size();
    }

    /**
     *
     * @see java.util.Map#values()
     */
    @Override
    public Collection<V> values()
    {
        // TODO Auto-generated method stub
        return null;
    }
}