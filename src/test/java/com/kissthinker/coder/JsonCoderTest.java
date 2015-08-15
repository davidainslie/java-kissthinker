package com.kissthinker.coder;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author David Ainslie
 *
 */
public class JsonCoderTest
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonCoderTest.class);

    /** */
    public JsonCoderTest()
    {
        super();
    }

    /** */
    @Test
    public void encodeAndDecode()
    {
        StopWatch stopWatch = new StopWatch();
        Coder coder = new JsonCoder();

        stopWatch.start();
        byte[] bytes = coder.encode(new Bean());
        stopWatch.stop();

        LOGGER.info("Encoded {} bytes in {}", bytes.length, stopWatch);
        LOGGER.trace("Encoded bytes: {}", new String(bytes));

        assertThat(bytes.length, greaterThan(1000));

        Bean bean = coder.decode(bytes);
        assertEquals(bean.getAge(), 42);
    }

    /** */
    @Test
    public void prettyEncode()
    {
        StopWatch stopWatch = new StopWatch();
        JsonCoder jsonCoder = new JsonCoder();

        stopWatch.start();
        byte[] bytes = jsonCoder.prettyEncode(new Bean());
        stopWatch.stop();

        LOGGER.info("Encoded {} bytes in {}", bytes.length, stopWatch);
        LOGGER.trace("Encoded bytes: {}", new String(bytes));

        assertThat(bytes.length, greaterThan(1000));
    }
}