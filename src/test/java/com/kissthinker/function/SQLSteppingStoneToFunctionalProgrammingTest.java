package com.kissthinker.function;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Before;
import org.junit.Test;
import static com.kissthinker.function.Fn.from;
import static com.kissthinker.function.Fn.function;
import static org.junit.Assert.assertEquals;

/**
 * Test functions written to be equivalent to certain Oracle SQL functions as an aid to understanding FP via KISS FP.
 * <br/>
 * @author David Ainslie
 *
 */
public class SQLSteppingStoneToFunctionalProgrammingTest
{
    /** */
    private String scoobyTrimmed;
    
    /** */
    private String scoobyToBeLTrimmed;
    
    /** */
    private String scoobyToBeRTrimmed;
    
    /** */
    private String scoobyDooTrimmed;
    
    /** */
    private String scoobyDooToBeLTrimmed;
    
    /** */
    private String scoobyDooToBeRTrimmed;
        
    /**
     * 
     */
    public SQLSteppingStoneToFunctionalProgrammingTest()
    {
        super();
    }

    /**
     * 
     */
    @Before
    public void initialise()
    {
        scoobyTrimmed = "Scooby";
        scoobyToBeLTrimmed = "    Scooby";
        scoobyToBeRTrimmed = "Scooby    ";
        
        scoobyDooTrimmed = "Scooby Doo";
        scoobyDooToBeLTrimmed = "    Scooby Doo";
        scoobyDooToBeRTrimmed = "Scooby Doo    ";
    }

    /**
     * Equivalent of an Oracle SQL "ltrim" e.g.<br/>
     * select ltrim("    Scooby") from dual;
     */
    @Test
    public void ltrim()
    {
        assertEquals(scoobyTrimmed, ltrim(scoobyToBeLTrimmed));
        assertEquals(scoobyDooTrimmed, ltrim(scoobyDooToBeLTrimmed));
    }

    /**
     * KISS FP equivalent of an Oracle SQL "ltrim" as a follow on from {@link #ltrim()}
     */
    @Test
    public void ltrimFP()
    {
        Function<String> ltrim = function(from(this).ltrim(null));
        
        assertEquals(scoobyTrimmed, ltrim.apply(scoobyToBeLTrimmed));
        assertEquals(scoobyDooTrimmed, ltrim.apply(scoobyDooToBeLTrimmed));
    }
    
    /**
     * Equivalent of an Oracle SQL "rtrim" e.g.<br/>
     * select rtrim("Scooby    ") from dual;
     */
    @Test
    public void rtrim()
    {
        assertEquals(scoobyTrimmed, rtrim(scoobyToBeRTrimmed));
        assertEquals(scoobyDooTrimmed, rtrim(scoobyDooToBeRTrimmed));
    }

    /**
     * KISS FP equivalent of an Oracle SQL "rtrim" as a follow on from {@link #rtrim()}
     */
    @Test
    public void rtrimFP()
    {
        Function<String> rtrim = function(from(this).rtrim(null));
        
        assertEquals(scoobyTrimmed, rtrim.apply(scoobyToBeRTrimmed));
        assertEquals(scoobyDooTrimmed, rtrim.apply(scoobyDooToBeRTrimmed));
    }

    /**
     * 
     * @param string
     * @return
     */
    public String ltrim(String string)
    {
        Pattern pattern = Pattern.compile("(\\A\\s*)(.*)");
        Matcher matcher = pattern.matcher(string);
        
        if (matcher.find())
        {
            return matcher.group(2);
        }
        
        return string;
    }
    
    /**
     * 
     * @param string
     * @return
     */
    public String rtrim(String string)
    {
        Pattern pattern = Pattern.compile("(.*?)(\\s*$)");
        Matcher matcher = pattern.matcher(string);
        
        if (matcher.find())
        {
            return matcher.group(1);
        }
        
        return string;
    }
}