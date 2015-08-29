package com.kissthinker.javabean;

import java.beans.PropertyChangeEvent;

/**
 * @author David Ainslie
 *
 */
public class PropertyEvent<V> extends PropertyChangeEvent
{
    /** */
    private static final long serialVersionUID = 1L;

    /**
     *
     * @param <S> Source type
     * @param source Source
     * @param property Property
     * @param oldValue Old value
     * @param newValue New value
     */
    public <S> PropertyEvent(S source, String property, V oldValue, V newValue)
    {
        super(source, property, oldValue, newValue);
    }

    /**
     *
     * @param <S> Source type
     * @param source Source
     * @param property Property
     * @param newValue New value
     */
    public <S> PropertyEvent(S source, String property, V newValue)
    {
        super(source, property, null, newValue);
    }

    /**
     *
     * @param propertyChangeEvent Property change event
     */
    public PropertyEvent(PropertyChangeEvent propertyChangeEvent)
    {
        super(propertyChangeEvent.getSource(), propertyChangeEvent.getPropertyName(), propertyChangeEvent.getOldValue(), propertyChangeEvent.getNewValue());
        setPropagationId(propertyChangeEvent.getPropagationId());
    }

    /**
     *
     * @param <S> Source type
     * @return Source
     */
    @SuppressWarnings("unchecked")
    public <S> S source()
    {
        return (S)super.getSource();
    }
    
    /**
     * 
     * @return Old value
     */
    public V oldValue()
    {
        return getOldValue();
    }

    /**
     *
     * @see java.beans.PropertyChangeEvent#getOldValue()
     */
    @Override
    @SuppressWarnings("unchecked")
    public V getOldValue()
    {
        return (V)super.getOldValue();
    }

    /**
     * 
     * @return New value
     */
    public V newValue()
    {
        return getNewValue();
    }

    /**
     *
     * @see java.beans.PropertyChangeEvent#getNewValue()
     */
    @Override
    @SuppressWarnings("unchecked")
    public V getNewValue()
    {
        return (V)super.getNewValue();
    }
}