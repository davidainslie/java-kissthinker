package com.kissthinker.configure;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.kissthinker.configure.configurer.ConfigurationClasses;
import static org.junit.Assert.assertTrue;

/**
 * @author David Ainslie
 * 
 */
public class ConfigureLoggerTest
{
    /** */
    private static final PrintStream SYSTEM_OUT_ORIGINAL = System.out;
    
    /** */
    private static final ByteArrayOutputStream SYSTEM_OUT_REDIRECT = new ByteArrayOutputStream();

    /**
     * Redirect "system out".
     */
    @BeforeClass
    public static void initialise()
    {
        System.setOut(new PrintStream(SYSTEM_OUT_REDIRECT));
    }

    /**
     * Undo the redirect "system out".
     */
    @AfterClass
    public static void finalise()
    {
        System.setOut(SYSTEM_OUT_ORIGINAL);
    }

    /**
     * Check that redirect works
     */
    @Test
    public void systemOut()
    {
        String message = "Testing 1, 2, 3";
        System.out.print(message);
        assertTrue(SYSTEM_OUT_REDIRECT.toString().contains(message));
    }

    /**
     * Check an instantiated object had a "logger" injected by requesting the object log a message, and check that message appears on "system out".
     */
    @Test
    public void configure()
    {
        System.out.println(ConfigurationClasses.configurationClasses());

        String message = "This message should be logged";
        
        LoggingBean loggingBean = new LoggingBean();
        loggingBean.log(message);
        assertTrue(SYSTEM_OUT_REDIRECT.toString().contains(message));
    }
}