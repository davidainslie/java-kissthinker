package com.kissthinker.configure;

import static com.kissthinker.system.Environment.TEST;

/**
 * @author David Ainslie
 *
 */
@Configuration(environment = TEST)
public class SystemPropertyByIDBean2 extends Bean2
{
    /** */
    private final String name = getClass().getName();

    /** */
    public SystemPropertyByIDBean2()
    {
        super();
    }

    /**
     * Getter
     * @return String
     */
    public String name()
    {
        return name;
    }
}