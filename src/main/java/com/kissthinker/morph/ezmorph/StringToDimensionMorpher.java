package com.kissthinker.morph.ezmorph;

import java.awt.Dimension;

/**
 * @author David Ainslie
 *
 */
public class StringToDimensionMorpher extends AbstractObjectMorpher
{
    /** */
    public StringToDimensionMorpher()
    {
        super(String.class, Dimension.class);
    }

    /** */
    @Override
    public Object morph(Object object)
    {
        String[] widthAndHeight = object.toString().split(",");

        return new Dimension(Integer.parseInt(widthAndHeight[0].trim()), Integer.parseInt(widthAndHeight[1].trim()));
    }
}