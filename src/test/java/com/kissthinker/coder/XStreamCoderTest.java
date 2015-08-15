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
public class XStreamCoderTest
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(XStreamCoderTest.class);

    /** */
    public XStreamCoderTest()
    {
        super();
    }

    /** */
    @Test
    public void encodeAndDecode()
    {
        StopWatch stopWatch = new StopWatch();
        Coder coder = new XStreamCoder();

        stopWatch.start();
        byte[] bytes = coder.encode(new Bean());
        stopWatch.stop();

        LOGGER.info("Encoded {} bytes in {}", bytes.length, stopWatch);
        LOGGER.trace("Encoded bytes: {}", new String(bytes));

        assertThat(bytes.length, greaterThan(1000));

        Bean bean = coder.decode(bytes);
        assertEquals(bean.getAge(), 42);
    }
}