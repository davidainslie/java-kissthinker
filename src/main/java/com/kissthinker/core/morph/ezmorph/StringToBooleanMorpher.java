package com.kissthinker.core.morph.ezmorph;

/**
 * @author David Ainslie
 *
 */
public class StringToBooleanMorpher extends AbstractObjectMorpher
{
    /** */
    public StringToBooleanMorpher()
    {
        super(String.class, Boolean.class);
    }

    /** */
    @Override
    public Object morph(Object object)
    {
        return new Boolean(object.toString());
    }
}