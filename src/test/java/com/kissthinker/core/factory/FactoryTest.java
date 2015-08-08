package com.kissthinker.core.factory;


import org.junit.Test;


/**
 * @author David Ainslie
 *
 */
public class FactoryTest
{
    /**
     *
     */
    @Test
    public void create()
    {
        String string = Factory.create(String.class);
        System.out.println(">>" + string);
    }
}