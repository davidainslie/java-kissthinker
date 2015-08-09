package com.kissthinker.core.coder;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.BeforeClass;
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
    @BeforeClass
    public static void initialiseClass()
    {
        // TODO The following should not be required as logback should automatically fine it.
        // However, even the following does not help.
        System.setProperty("logback.configurationFile", "logback-test.xml");
    }

    /** */
    public JsonSmartCoderTest()
    {
        super();
    }

    /** */
    @Test
    public void encode()
    {
        LOGGER.trace("Should be logged (if logback-test.xml was read) but it is not.");

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