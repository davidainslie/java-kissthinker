package com.kissthinker.core.collection;

/**
 * @author David Ainslie
 *
 */
public interface CollectionListener<O>
{
    /**
     * 
     * @param object
     */
    void onAdd(O object);

    /**
     * 
     * @param object
     */
    void onRemove(O object);
}