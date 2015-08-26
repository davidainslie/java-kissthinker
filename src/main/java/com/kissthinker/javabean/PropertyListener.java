package com.kissthinker.javabean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author David Ainslie
 *
 */
public abstract class PropertyListener<V> implements PropertyChangeListener
{
    /**
     *
     * @param propertyEvent PropertyEvent
     */
    public abstract void propertyChange(PropertyEvent<V> propertyEvent);

    /**
     *
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent)
    {
        if (propertyChangeEvent instanceof PropertyEvent)
        {
            propertyChange((PropertyEvent<V>)propertyChangeEvent);
        }
        else  // Handle a normal property change event i.e. one that is not generic.
        {
            propertyChange(new PropertyEvent<>(propertyChangeEvent));  // Create a copy of the event.
        }
    }
}