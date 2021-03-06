package com.kissthinker.morph;

import com.kissthinker.morph.ezmorph.EZMorpher;

/**
 * Morph an object to another type of object.
 *
 * {@link EZMorpher} is the default implementation to handle morphing from one class type to another.
 * TODO Set up unit test to use different one morph implementation e.g change from currently using EZMorph to say Apache Commons ConvertUtils.
 * NOTE Instead of using EZMorph, could have used Apache Commons ConvertUtils instead e.g.
 * Converter myConverter = new org.apache.commons.beanutils.converter.IntegerConverter();
 * ConvertUtils.register(myConverter, Integer.TYPE);    // Native type
 * ConvertUtils.register(myConverter, Integer.class);   // Wrapper class
 * Other open sources that could be utilised are:
 * http://dozer.sourceforge.net/
 * http://morph.sourceforge.net/
 * @author David Ainslie
 *
 */
public interface Morpher
{
    /**
     * Morph (transform/convert) given object to the required class i.e to an object of type toClass, Class
     * @param <O> Object type
     * @param object Object
     * @param toClass Class to morph object to
     * @return O
     */
    <O> O morph(Object object, Class<O> toClass);
}