package com.kissthinker.core.configure;


/**
 * Note that this implementation is not annotated with @{@link Configuration}<br/>
 * any so is not considered during configuration and so may be overwritten even when declared "final"777
 * @author David Ainslie
 *
 */
public class NotToBeReplacedBean2 extends Bean2
{
    /** */
    private final String name = getClass().getName();


    /**
     *
     */
    public NotToBeReplacedBean2()
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