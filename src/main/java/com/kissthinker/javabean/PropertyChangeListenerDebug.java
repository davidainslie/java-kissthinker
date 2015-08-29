package com.kissthinker.javabean;

import java.beans.PropertyChangeListener;
import java.lang.ref.WeakReference;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author David Ainslie
 *
 */
@Aspect
public class PropertyChangeListenerDebug
{
    /** */
    private static WeakReference<PropertyChangeListener> propertyChangeListenerWeakReference;

    /** */
    public static void reset()
    {
        propertyChangeListenerWeakReference = null;
    }

    /**
     *
     * @return Property change listener
     */
    public static PropertyChangeListener propertyChangeListener()
    {
        if (propertyChangeListenerWeakReference == null)
        {
            return null;
        }
        
        return propertyChangeListenerWeakReference.get();
    }

    /** */
    public PropertyChangeListenerDebug()
    {
        super();
    }

    /**
     * 
     * @param propertyChangeListener PropertyChangeListener
     */
    @After("execution(java.beans.PropertyChangeListener+.new(..)) && this(propertyChangeListener)")
    public void propertyChangeListenerInstantiated(final PropertyChangeListener propertyChangeListener)
    {
        propertyChangeListenerWeakReference = new WeakReference<>(propertyChangeListener);
    }
}