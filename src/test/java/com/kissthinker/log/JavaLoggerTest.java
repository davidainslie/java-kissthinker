package com.kissthinker.log;

import java.util.logging.Logger;

/**
 * @author David Ainslie
 *
 */
public class JavaLoggerTest
{
    /***/
    private static final Logger LOGGER = Logger.getLogger(JavaLoggerTest.class.getName());

    /** */
    public JavaLoggerTest()
    {
        super();
        LOGGER.info("What an effort");
    }
}