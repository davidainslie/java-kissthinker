package com.kissthinker.core.configure;

import static java.text.MessageFormat.format;

/**
 * @author David Ainslie
 *
 */
@Configurable
public class Bean3
{
    /** */
    @Configure(id="property1", otherwise="Otherwise")
    private static final String STATIC_STRING = null;

    /** */
    @Configure(id="property2", otherwise="99")
    private final Integer integer = null;

    /** */
    public Bean3()
    {
        super();
    }

    /** */
    public static String staticString()
    {
        return STATIC_STRING;
    }

    /** */
    public Integer integer()
    {
        return integer;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return format("Bean3 [staticString={0}, integer={1}]", STATIC_STRING, integer);
    }
}