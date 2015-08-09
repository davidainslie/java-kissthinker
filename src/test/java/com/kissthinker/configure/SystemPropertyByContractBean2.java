package com.kissthinker.configure;

import static com.kissthinker.system.Environment.TEST;

/**
 * @author David Ainslie
 *
 */
@Configuration(environment=TEST)
public class SystemPropertyByContractBean2 extends Bean2
{
    /** */
    private final String name = getClass().getName();

    /** */
    public SystemPropertyByContractBean2()
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