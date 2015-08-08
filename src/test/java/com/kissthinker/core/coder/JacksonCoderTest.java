package com.kissthinker.core.coder;


import org.apache.commons.lang3.time.StopWatch;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kissthinker.core.coder.Coder;
import com.kissthinker.core.coder.JacksonCoder;


/**
 * @author David Ainslie
 *
 */
public class JacksonCoderTest
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
    public JacksonCoderTest()
    {
        super();
    }


    /**
     *
     */
    @Test
    public void encode()
    {
        StopWatch stopWatch = new StopWatch();
        Coder coder = new JacksonCoder();

        stopWatch.start();
        byte[] bytes = coder.encode(new Bean());
        stopWatch.stop();

        System.out.printf("Encoded %d bytes in %s:%n%s%n", bytes.length, stopWatch, new String(bytes));
    }


    /**
     *
     */
    @Test
    public void prettyEncode()
    {
        StopWatch stopWatch = new StopWatch();
        JacksonCoder jacksonCoder = new JacksonCoder();

        stopWatch.start();
        byte[] bytes = jacksonCoder.prettyEncode(new Bean());
        stopWatch.stop();

        System.out.printf("Encoded %d bytes in %s:%n%s%n", bytes.length, stopWatch, new String(bytes));
    }
}