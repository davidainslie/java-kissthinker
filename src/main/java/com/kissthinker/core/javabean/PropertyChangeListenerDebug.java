package com.kissthinker.core.javabean;

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

    /** */
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
     * @param propertyChangeListener
     */
    @After("execution(java.beans.PropertyChangeListener+.new(..)) && this(propertyChangeListener)")
    public void propertyChangeListenerInstantiated(final PropertyChangeListener propertyChangeListener)
    {
        propertyChangeListenerWeakReference = new WeakReference<>(propertyChangeListener);
    }
}