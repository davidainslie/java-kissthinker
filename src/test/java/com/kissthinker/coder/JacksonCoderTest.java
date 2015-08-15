package com.kissthinker.coder;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

/**
 * @author David Ainslie
 *
 */
public class JacksonCoderTest
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonCoderTest.class);

    /** */
    public JacksonCoderTest()
    {
        super();
    }

    /** */
    @Test
    public void encode()
    {
        StopWatch stopWatch = new StopWatch();
        Coder coder = new JacksonCoder();

        stopWatch.start();
        byte[] bytes = coder.encode(new Bean());
        stopWatch.stop();

        LOGGER.info("Encoded {} bytes in {}", bytes.length, stopWatch);
        LOGGER.trace("Encoded bytes: {}", new String(bytes));

        assertThat(bytes.length, greaterThan(1000));
    }

    /** */
    @Test
    public void prettyEncode()
    {
        StopWatch stopWatch = new StopWatch();
        JacksonCoder jacksonCoder = new JacksonCoder();

        stopWatch.start();
        byte[] bytes = jacksonCoder.prettyEncode(new Bean());
        stopWatch.stop();

        LOGGER.info("Encoded {} bytes in {}", bytes.length, stopWatch);
        LOGGER.trace("Encoded bytes: {}", new String(bytes));

        assertThat(bytes.length, greaterThan(1000));
    }
}