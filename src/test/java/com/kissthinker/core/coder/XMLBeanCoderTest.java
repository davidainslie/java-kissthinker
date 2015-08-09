package com.kissthinker.core.coder;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author David Ainslie
 *
 */
public class XMLBeanCoderTest
{
    /** */
    @BeforeClass
    public static void initialiseClass()
    {
        System.setProperty("logback.configurationFile", "logback-test.xml");
    }

    /** */
    public XMLBeanCoderTest()
    {
        super();
    }

    /** */
    @Test
    public void encode()
    {
        StopWatch stopWatch = new StopWatch();
        Coder coder = new XMLBeanCoder();

        stopWatch.start();
        byte[] bytes = coder.encode(new Bean());
        stopWatch.stop();

        System.out.printf("Encoded %d bytes in %s:%n%s%n", bytes.length, stopWatch, new String(bytes));
    }
}