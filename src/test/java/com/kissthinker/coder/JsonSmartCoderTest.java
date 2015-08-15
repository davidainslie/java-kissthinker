package com.kissthinker.coder;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interesting - nothing encoded and so nothing decoded - kind of useless!
 * <br/>
 * @author David Ainslie
 *
 */
public class JsonSmartCoderTest
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonSmartCoderTest.class);

    /** */
    public JsonSmartCoderTest()
    {
        super();
    }

    /** */
    @Test
    @Ignore
    public void encode()
    {
        StopWatch stopWatch = new StopWatch();
        Coder coder = new JsonSmartCoder();

        stopWatch.start();
        byte[] bytes = coder.encode(new Bean());
        stopWatch.stop();

        System.out.printf("Encoded %d bytes in %s:%n%s%n", bytes.length, stopWatch, new String(bytes));

        Bean bean = coder.decode(bytes);
        System.out.println(bean);
    }
}