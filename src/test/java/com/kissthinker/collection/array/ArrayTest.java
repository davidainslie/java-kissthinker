package com.kissthinker.collection.array;

import java.io.Serializable;
import java.util.Arrays;
import org.junit.Test;

/**
 * Just experimenting.
 * <p>
 * TODO Delete this.
 * @author David Ainslie
 *
 */
public class ArrayTest
{
    /** */
    public ArrayTest()
    {
        super();
    }

    /** */
    @Test
    public void test()
    {
        Object[] objects = new Object[]{"Scooby", "Scrappy"};
        Serializable[] serializables = Arrays.copyOf(objects, objects.length, Serializable[].class);

        System.out.println(Arrays.toString(serializables));
    }

    /**
     *
     * @author David Ainslie
     *
     */
    static class Test1
    {
        Object[] objects;

        Test1(Object[] objects)
        {
            this.objects = objects;
        }
    }

    /**
     *
     * @author David Ainslie
     *
     */
    static class Test2 extends Test1
    {
        Test2(Serializable... objects)
        {
            super((Object[])objects);
        }
    }
}