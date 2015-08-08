package com.kissthinker.core.coder;


import org.apache.commons.lang3.time.StopWatch;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kissthinker.core.coder.Coder;
import com.kissthinker.core.coder.GsonCoder;


/**
 * @author David Ainslie
 *
 */
public class GsonCoderTest
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(GsonCoderTest.class);


    /**
     *
     */
    @BeforeClass
    public static void initialiseClass()
    {
        // TODO The following should not be required as logback should automatically fine it.
        // However, even the following does not help.
        System.setProperty("logback.configurationFile", "logback-test.xml");
    }


    /**
     *
     */
    public GsonCoderTest()
    {
        super();
    }


    /**
     *
     */
    @Test
    public void encode()
    {
        LOGGER.trace("Should be logged (if logback-test.xml was read) but it is not.");

        StopWatch stopWatch = new StopWatch();
        Coder coder = new GsonCoder();

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
        GsonCoder gsonCoder = new GsonCoder();

        stopWatch.start();
        byte[] bytes = gsonCoder.prettyEncode(new Bean());
        stopWatch.stop();

        System.out.printf("Encoded %d bytes in %s:%n%s%n", bytes.length, stopWatch, new String(bytes));
    }
}