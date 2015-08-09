package com.kissthinker.javabean;

import java.beans.PropertyChangeSupport;

/**
 * This aspect injects extra functionality onto a JavaBean much like a trait in Scala.
 * @author David Ainslie
 */
public aspect PropertyChangeSupporterImpl
{
    /** */
    declare parents: JavaBean extends PropertyChangeSupporter;

    /** Important this is transient so that this "injected" field is ignored during any kind of serialisation/deserialisation. */
    private transient PropertyChangeSupport JavaBean.propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Implementation required by interface (contract) PropertyChangeSupporter.
     */
    public PropertyChangeSupport JavaBean.propertyChangeSupport()
    {
        return propertyChangeSupport;
    }
}