package com.kissthinker.core.collection.map;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author David Ainslie
 * 
 */
public class IdentityWeakHashMap<K, V> implements Map<K, V>
{
    /** */
    private final ReferenceQueue<K> queue = new ReferenceQueue<>();
    
    /** */
    private final Map<IdentityWeakReference, V> backingStore = new HashMap<>();

    /** */
    public IdentityWeakHashMap()
    {
    }

    /**
     * 
     * @see java.util.Map#clear()
     */
    @Override
    public void clear()
    {
        backingStore.clear();
        reap();
    }

    /**
     * 
     * @see java.util.Map#containsKey(java.lang.Object)
     */
    @Override
    public boolean containsKey(Object key)
    {
        reap();
        return backingStore.containsKey(new IdentityWeakReference(key));
    }

    /**
     * 
     * @see java.util.Map#containsValue(java.lang.Object)
     */
    @Override
    public boolean containsValue(Object value)
    {
        reap();
        return backingStore.containsValue(value);
    }

    /**
     * 
     * @see java.util.Map#entrySet()
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet()
    {
        reap();
        Set<Map.Entry<K, V>> ret = new HashSet<>();
        
        for (Map.Entry<IdentityWeakReference, V> ref : backingStore.entrySet())
        {
            final K key = ref.getKey().get();
            final V value = ref.getValue();
            
            Map.Entry<K, V> entry = new Map.Entry<K, V>()
            {
                @Override
                public K getKey()
                {
                    return key;
                }

                @Override
                public V getValue()
                {
                    return value;
                }


                @Override
                public V setValue(V value)
                {
                    throw new UnsupportedOperationException();
                }
            };
            
            ret.add(entry);
        }
        
        return Collections.unmodifiableSet(ret);
    }

    /**
     * 
     * @see java.util.Map#keySet()
     */
    @Override
    public Set<K> keySet()
    {
        reap();
        Set<K> ret = backingStore.keySet().stream().map(IdentityWeakReference::get).collect(Collectors.toSet());

        return Collections.unmodifiableSet(ret);
    }

    /**
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o)
    {
        return backingStore.equals(((IdentityWeakHashMap<?, ?>)o).backingStore);
    }

    /**
     * 
     * @see java.util.Map#get(java.lang.Object)
     */
    @Override
    public V get(Object key)
    {
        reap();
        return backingStore.get(new IdentityWeakReference(key));
    }

    /**
     * 
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public V put(K key, V value)
    {
        reap();
        return backingStore.put(new IdentityWeakReference(key), value);
    }

    /**
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        reap();
        return backingStore.hashCode();
    }

    /**
     * 
     * @see java.util.Map#isEmpty()
     */
    @Override
    public boolean isEmpty()
    {
        reap();
        return backingStore.isEmpty();
    }

    /**
     * 
     * @see java.util.Map#putAll(java.util.Map)
     */
    @Override
    public void putAll(@SuppressWarnings("rawtypes") Map t)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * 
     * @see java.util.Map#remove(java.lang.Object)
     */
    @Override
    public V remove(Object key)
    {
        reap();
        return backingStore.remove(new IdentityWeakReference(key));
    }

    /**
     * 
     * @see java.util.Map#size()
     */
    @Override
    public int size()
    {
        reap();
        return backingStore.size();
    }

    /**
     * 
     * @see java.util.Map#values()
     */
    @Override
    public Collection<V> values()
    {
        reap();
        return backingStore.values();
    }

    /**
     * 
     */
    private synchronized void reap()
    {
        Object zombie = queue.poll();

        while (zombie != null)
        {
            @SuppressWarnings("unchecked")
            IdentityWeakReference victim = (IdentityWeakReference)zombie;
            backingStore.remove(victim);
            zombie = queue.poll();
        }
    }

    /**
     * 
     * @author David Ainslie
     *
     */
    class IdentityWeakReference extends WeakReference<K>
    {
        /** */
        private final int hash;

        /**
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode()
        {
            return hash;
        }

        /**
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            
            @SuppressWarnings("unchecked")
            IdentityWeakReference ref = (IdentityWeakReference)o;
            
            if (this.get() == ref.get())
            {
                return true;
            }
            
            return false;
        }

        /**
         * 
         * @param obj
         */
        @SuppressWarnings("unchecked")
        IdentityWeakReference(Object obj)
        {
            super((K)obj, queue);
            hash = System.identityHashCode(obj);
        }
    }
}