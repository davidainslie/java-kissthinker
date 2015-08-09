package com.kissthinker.configure;


import static com.kissthinker.system.Environment.TEST;;


/**
 * @author David Ainslie
 *
 */
@Configuration(environment=TEST)
public class Bean2
{
    /** */
    private final String name = getClass().getName();


    /**
     *
     */
    public Bean2()
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