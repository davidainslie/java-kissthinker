package com.kissthinker.javabean;

import java.beans.PropertyChangeSupport;

/**
 * @author David Ainslie
 *
 */
public interface PropertyChangeSupporter
{
    /**
     *
     * @return PropertyChangeSupport
     */
    PropertyChangeSupport propertyChangeSupport();
}