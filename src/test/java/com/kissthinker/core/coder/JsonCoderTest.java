package com.kissthinker.core.coder;


import org.apache.commons.lang3.time.StopWatch;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kissthinker.core.coder.Coder;
import com.kissthinker.core.coder.JsonCoder;


/**
 * @author David Ainslie
 *
 */
public class JsonCoderTest
{
    /**
     *
     */
    @BeforeClass
    public static void initialiseClass()
    {
        System.setProperty("logback.configurationFile", "logback-test.xml");
    }


    /**
     *
     */
    public JsonCoderTest()
    {
        super();
    }


    /**
     * TODO Bad test, as there are "log statements" instead of assertions;
     */
    @Test
    public void encodeAndDecode()
    {
        StopWatch stopWatch = new StopWatch();
        Coder coder = new JsonCoder();

        stopWatch.start();
        byte[] bytes = coder.encode(new Bean());
        stopWatch.stop();

        System.out.printf("Encoded %d bytes in %s:%n%s%n", bytes.length, stopWatch, new String(bytes));

        Bean bean = coder.decode(bytes);
        System.out.println(bean);
    }


    /**
     * TODO Bad test, as there are "log statements" instead of assertions;
     */
    @Test
    public void prettyEncode()
    {
        StopWatch stopWatch = new StopWatch();
        JsonCoder jsonCoder = new JsonCoder();

        stopWatch.start();
        byte[] bytes = jsonCoder.prettyEncode(new Bean());
        stopWatch.stop();

        System.out.printf("Encoded %d bytes in %s:%n%s%n", bytes.length, stopWatch, new String(bytes));
    }
}