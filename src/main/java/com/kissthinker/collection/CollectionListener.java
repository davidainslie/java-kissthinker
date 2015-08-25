package com.kissthinker.collection;

/**
 * @author David Ainslie
 *
 */
public interface CollectionListener<O>
{
    /**
     * 
     * @param object Object that has been added to collection
     */
    void onAdd(O object);

    /**
     * 
     * @param object Object that has been removed from collection
     */
    void onRemove(O object);
}