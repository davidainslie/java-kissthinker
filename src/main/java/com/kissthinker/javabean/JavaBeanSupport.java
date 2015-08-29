package com.kissthinker.javabean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import com.kissthinker.collection.CollectionListener;
import com.kissthinker.collection.map.MapListener;
import com.kissthinker.object.Singleton;
import com.kissthinker.reflect.MethodUtil;
import static com.kissthinker.collection.map.MapUtil.identityWeakHashMap;
import static com.kissthinker.collection.set.SetUtil.hashSet;

/**
 * PropertyChangeListener broker.
 * <p>
 * Notice that there are no "unlisten" methods to match {@link #listen(JavaBean, PropertyChangeListener)} or {@link #listen(JavaBean, String, PropertyChangeListener)}.<br>
 * As listeners are managed weakly, then (like garbage collection) listener "clearance" is handled automatically.
 * @author David Ainslie
 * 
 */
@Singleton
public final class JavaBeanSupport
{
    /** TODO Test for auto GC - may need a slight rewrite */
    private static final Map<Collection<?>, Set<CollectionListener<?>>> COLLECTION_LISTENERS = identityWeakHashMap();
    
    /** */
    private static final Lock COLLECTION_LISTENERS_LOCK = new ReentrantLock();
    
    /** TODO Test for auto GC - may need a slight rewrite */
    private static final Map<Map<?, ?>, Set<MapListener<?, ?>>> MAP_LISTENERS = identityWeakHashMap();
    
    /** */
    private static final Lock MAP_LISTENERS_LOCK = new ReentrantLock();

    /**
     * 
     * @param javaBean JavaBean
     * @param propertyChangeListener PropertyChangeListener
     * @return JavaBean
     */
    public static JavaBean listen(JavaBean javaBean, PropertyChangeListener propertyChangeListener)
    {
        propertyChangeSupport(javaBean).addPropertyChangeListener(propertyChangeListener);
        return javaBean;
    }

    /**
     * 
     * @param javaBean JavaBean
     * @param propertyName Property name
     * @param propertyChangeListener PropertyChangeListener
     * @param <P> Property type
     * @return JavaBean
     */
    public static <P> JavaBean listen(JavaBean javaBean, String propertyName, PropertyChangeListener propertyChangeListener)
    {        
        propertyChangeSupport(javaBean).addPropertyChangeListener(propertyName, propertyChangeListener);
        return javaBean;
    }
    
    /**
     * 
     * @param javaBean JavaBean
     * @param propertyName Property name
     * @param propertyChangeListener PropertyChangeListener
     * @param <P> Property type
     * @return JavaBean.Enumerated
     */
    public static <P> JavaBean.Enumerated<?> listen(JavaBean.Enumerated<?> javaBean, String propertyName, PropertyChangeListener propertyChangeListener)
    {        
        propertyChangeSupport(javaBean).addPropertyChangeListener(propertyName, propertyChangeListener);
        return javaBean;
    }

    /**
     * 
     * @param javaBean JavaBean
     * @param property Property
     * @param propertyChangeListener PropertyChangeListener
     * @param <P> Property type
     * @return JavaBean.Enumerated
     */
    public static <P> JavaBean.Enumerated<?> listen(JavaBean.Enumerated<?> javaBean, P property, PropertyChangeListener propertyChangeListener)
    {        
        propertyChangeSupport(javaBean).addPropertyChangeListener(property.toString(), propertyChangeListener);
        return javaBean;
    }

    /**
     * 
     * @param collection Collection
     * @param collectionListener PropertyChangeListener
     * @param <O> Object type
     */
    public static <O> void listen(Collection<O> collection, CollectionListener<O> collectionListener)
    {
        COLLECTION_LISTENERS_LOCK.lock();
        Set<CollectionListener<?>> collectionListeners = null;
        
        try
        {
            collectionListeners = COLLECTION_LISTENERS.get(collection);
            
            if (collectionListeners == null)
            {
                collectionListeners = hashSet();
                COLLECTION_LISTENERS.put(collection, collectionListeners);
            }
        }
        finally
        {
            COLLECTION_LISTENERS_LOCK.unlock();
        }
        
        collectionListeners.add(collectionListener);
    }

    /**
     *
     * @param map Map
     * @param mapListener Map listener
     * @param <K> Key type
     * @param <V> Value type
     */
    public static <K, V> void listen(Map<K, V> map, MapListener<K, V> mapListener)
    {
        MAP_LISTENERS_LOCK.lock();
        Set<MapListener<?, ?>> mapListeners = null;
        
        try
        {
            mapListeners = MAP_LISTENERS.get(map);
            
            if (mapListeners == null)
            {
                mapListeners = hashSet();
                MAP_LISTENERS.put(map, mapListeners);
            }
        }
        finally
        {
            MAP_LISTENERS_LOCK.unlock();
        }
        
        mapListeners.add(mapListener);
    }

    /**
     * 
     * @param methodName Method name
     * @param collection Collection
     * @param object Object
     */
    static void callback(final String methodName, final Collection<?> collection, final Object object)
    {
        final Set<CollectionListener<?>> collectionListeners = COLLECTION_LISTENERS.get(collection);
        
        if (collectionListeners != null)
        {
            // Note - Originally invoked the following on a separate thread via ExecutorUtil.execute.
            // However, the order of applying e.g. "onAdd" is then unpredictable, which is probably not to be expected.
            for (CollectionListener<?> collectionListener : collectionListeners)
            {
                MethodUtil.invoke(collectionListener, methodName, object);
            }
        }
    }

    /**
     *
     * @param methodName Method name
     * @param map Map
     * @param key Key
     * @param value Value
     */
    static void callback(final String methodName, final Map<?, ?> map, final Object key, final Object value)
    {
        final Set<MapListener<?, ?>> mapListeners = MAP_LISTENERS.get(map);
        
        if (mapListeners != null)
        {
            // Note - Originally invoked the following on a separate thread via ExecutorUtil.execute.
            // However, the order of applying e.g. "onPut" is then unpredictable, which is probably not to be expected.
            for (MapListener<?, ?> mapListener : mapListeners)
            {
                MethodUtil.invoke(mapListener, methodName, key, value);
            }
        }
    }

    /**
     * 
     * @param javaBean JavaBean
     * @param propertyName Property name
     * @param oldValue Old value
     * @param newValue New value
     * @return JavaBean
     */
    static JavaBean firePropertyChange(JavaBean javaBean, String propertyName, Object oldValue, Object newValue)
    {
        propertyChangeSupport(javaBean).firePropertyChange(propertyName, oldValue, newValue);
        return javaBean;
    }

    /**
     * 
     * @param javaBean JavaBean
     * @param propertyName Property name
     * @param oldValue Old value
     * @param newValue New value
     * @param <P> Property type
     * @return JavaBean.Enumerated
     */
    static <P> JavaBean.Enumerated<?> firePropertyChange(JavaBean.Enumerated<?> javaBean, String propertyName, Object oldValue, Object newValue)
    {
        propertyChangeSupport(javaBean).firePropertyChange(propertyName, oldValue, newValue);
        return javaBean;
    }

    /**
     * 
     * @param javaBean JavaBean
     * @param property Property
     * @param oldValue Old value
     * @param newValue New value
     * @param <P> Property type
     * @return JavaBean.Enumerated
     */
    static <P> JavaBean.Enumerated<?> firePropertyChange(JavaBean.Enumerated<?> javaBean, P property, Object oldValue, Object newValue)
    {
        propertyChangeSupport(javaBean).firePropertyChange(property.toString(), oldValue, newValue);
        return javaBean;
    }

    /**
     * 
     * @param javaBean JavaBean
     * @param propertyChangeEvent PropertyChangeEvent
     * @return JavaBean
     */
    static JavaBean firePropertyChange(JavaBean javaBean, PropertyChangeEvent propertyChangeEvent)
    {
        propertyChangeSupport(javaBean).firePropertyChange(propertyChangeEvent);
        return javaBean;
    }

    /**
     * 
     * @param javaBean JavaBean
     * @param propertyName Property name
     * @param index Index
     * @param oldValue Old value
     * @param newValue New value
     * @return JavaBean
     */
    static JavaBean fireIndexedPropertyChange(JavaBean javaBean, String propertyName, int index, Object oldValue, Object newValue)
    {
        propertyChangeSupport(javaBean).fireIndexedPropertyChange(propertyName, index, oldValue, newValue);
        return javaBean;
    }

    /**
     * 
     * @param javaBean JavaBean
     * @param propertyName Property name
     * @param index Index
     * @param oldValue Old value
     * @param newValue New value
     * @param <P> Property type
     * @return JavaBean.Enumerated
     */
    static <P> JavaBean.Enumerated<?> fireIndexedPropertyChange(JavaBean.Enumerated<?> javaBean, String propertyName, int index, Object oldValue, Object newValue)
    {
        propertyChangeSupport(javaBean).fireIndexedPropertyChange(propertyName, index, oldValue, newValue);
        return javaBean;
    }

    /**
     * 
     * @param javaBean JavaBean
     * @param property Property
     * @param index Index
     * @param oldValue Old value
     * @param newValue New value
     * @param <P> Property type
     * @return JavaBean.Enumerated
     */
    static <P> JavaBean.Enumerated<?> fireIndexedPropertyChange(JavaBean.Enumerated<?> javaBean, P property, int index, Object oldValue, Object newValue)
    {
        propertyChangeSupport(javaBean).fireIndexedPropertyChange(property.toString(), index, oldValue, newValue);
        return javaBean;
    }

    /**
     * Clear all sources and listeners - Removes everything from managed collections.
     */
    static void clear()
    {
        COLLECTION_LISTENERS_LOCK.lock();
        
        try
        {
            COLLECTION_LISTENERS.clear();
        }
        finally
        {
            COLLECTION_LISTENERS_LOCK.unlock();
        }
        
        MAP_LISTENERS_LOCK.lock();
        
        try
        {
            MAP_LISTENERS.clear();
        }
        finally
        {
            MAP_LISTENERS_LOCK.unlock();
        }
    }

    /**
     *
     * @return Collection listener count
     */
    static int collectionListenerCount()
    {
        return COLLECTION_LISTENERS.size();
    }


    /**
     *
     * @return Map listener count
     */
    static int mapListenerCount()
    {
        return MAP_LISTENERS.size();
    }
    
    
    /**
     * We can safely cast a given {@link JavaBean} to {@link PropertyChangeSupporter} as we have injected the interface and an implementation via AspectJ.<p>
     * Comparing this to Scala, {@link PropertyChangeSupporter} and its implementation act as a trait.
     * @param javaBean JavaBean
     * @return PropertyChangeSupport
     */
    private static PropertyChangeSupport propertyChangeSupport(JavaBean javaBean)
    {
        PropertyChangeSupporter propertyChangeSupporter = (PropertyChangeSupporter)javaBean;
        return propertyChangeSupporter.propertyChangeSupport();
    }

    /**
     * Utility.
     */
    private JavaBeanSupport()
    {
        super();
    }
}