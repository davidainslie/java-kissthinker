package com.kissthinker.coder;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Work in progress.
 * @author David Ainslie
 *
 */
public class FastJsonCoderTest
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(FastJsonCoderTest.class);

    /** */
    public FastJsonCoderTest()
    {
        super();
    }

    /** */
    @Test
    public void encode()
    {
        StopWatch stopWatch = new StopWatch();
        Coder coder = new FastJsonCoder();

        stopWatch.start();
        byte[] bytes = coder.encode(new Bean());
        stopWatch.stop();

        System.out.printf("Encoded %d bytes in %s:%n%s%n", bytes.length, stopWatch, new String(bytes));
    }

    /** */
    @Test
    public void prettyEncode()
    {
        StopWatch stopWatch = new StopWatch();
        FastJsonCoder fastJsonCoder = new FastJsonCoder();

        stopWatch.start();
        byte[] bytes = fastJsonCoder.prettyEncode(new Bean());
        stopWatch.stop();

        System.out.printf("Encoded %d bytes in %s:%n%s%n", bytes.length, stopWatch, new String(bytes));
    }
}