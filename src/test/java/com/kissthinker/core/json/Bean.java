package com.kissthinker.core.json;

import java.util.ArrayList;
import java.util.List;

/**
 * @author David Ainslie
 *
 */
public class Bean
{
    /** */
    private final int data1 = 100;

    /** */
    private final String data2 = "hello";

    /** */
    private final Bean2 data3 = new Bean2();

    /** */
    private final List<String> list = new ArrayList<String>()
    {
        private static final long serialVersionUID = 1L;

        {
            add("String 1");
            add("String 2");
            add("String 3");
        }
    };

    /**
     *
     * @param notUsed
     */
    public Bean(String notUsed)
    {
        super();
    }

    /**
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Bean [data1=" + data1 + ", data2=" + data2 + ", data3=" + data3 + ", list=" + list + "]";
    }
}