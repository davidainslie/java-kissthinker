package com.kissthinker.core.system;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import org.junit.Test;
import com.kissthinker.core.configure.Configuration;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author David Ainslie
 *
 */
public class EnvironmentTest
{
    /** */
    public EnvironmentTest()
    {
        super();
    }

    /** */
    @Test
    public void sorted()
    {
        Set<Environment> sortedEnvironments = Environment.sorted(Environment.LOCAL, Environment.DEV, Environment.LIVE);

        Queue<Environment> expectedEnvironments = new LinkedList<>();
        expectedEnvironments.add(Environment.LIVE);
        expectedEnvironments.add(Environment.DEV);
        expectedEnvironments.add(Environment.LOCAL);

        for (Environment environment : sortedEnvironments)
        {
            assertEquals(expectedEnvironments.remove(), environment);
        }
    }

    /** */
    @Test
    public void highestOrdinal()
    {
        int highestOrdinal = Environment.highestOrdinal(Environment.DEV, Environment.LOCAL, Environment.LIVE);
        assertEquals(Environment.LOCAL.ordinal(), highestOrdinal);
    }

    /** */
    @Test
    public void highestOrdinalForConfiguration()
    {
        @Configuration(environment=Environment.INTEGRATION)
        class Test1 {};

        @Configuration(environment={Environment.INTEGRATION, Environment.LOCAL})
        class Test2 {};

        int highestOrdinalForTest1 = Environment.highestOrdinal(Test1.class.getAnnotation(Configuration.class).environment());
        int highestOrdinalForTest2 = Environment.highestOrdinal(Test2.class.getAnnotation(Configuration.class).environment());

        assertTrue(highestOrdinalForTest2 > highestOrdinalForTest1);

        assertEquals(Environment.LOCAL.ordinal(), highestOrdinalForTest2);
        assertEquals(Environment.INTEGRATION.ordinal(), highestOrdinalForTest1);
    }
}