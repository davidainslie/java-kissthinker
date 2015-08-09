package com.kissthinker.configure;

import org.slf4j.Logger;

/**
 * @author David Ainslie
 *
 */
@Configurable
public class LoggingBean
{
    /** */
    @Configure
    private static final Logger LOGGER = null;

    /** */
    public LoggingBean()
    {
        super();
    }

    /**
     * 
     * @param message
     */
    public void log(String message)
    {
        LOGGER.info(message);
    }
}