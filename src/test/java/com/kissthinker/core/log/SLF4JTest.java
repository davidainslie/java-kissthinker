package com.kissthinker.core.log;


import static com.kissthinker.core.collection.array.ArrayUtil.va;
import static java.text.MessageFormat.format;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author David Ainslie
 *
 */
public class SLF4JTest
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(SLF4JTest.class);


    /**
     *
     */
    public SLF4JTest()
    {
        super();
        LOGGER.info("Less of an effort that standard Java Logger");
    }


    /**
     *
     */
    @Test
    public void log()
    {
        LOGGER.error("No connection to {}:{} after {} maximimum allowed retries because of {}",
                     va("localhost", 7, 5, "'exception message'"));
    }


    /**
     *
     */
    @Test
    public void formatLog()
    {
        String address = "localhost";
        int port = 7;
        String message = "'exception message'";
        int waitSeconds = 1;
        int retries = 4;
        LOGGER.warn(format("No connection to {0}:{1} because of \"{2}\"\nWill retry in {3,choice,0#0 seconds|1#1 second|1<{3,number,integer} seconds}, with {4,choice,0#no retries|1#1 retry|1<{4,number,integer} retries} remaining.",
                            address, port, message, waitSeconds, retries));
    }
}