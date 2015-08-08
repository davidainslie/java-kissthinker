package com.kissthinker.core.system;

/**
 * Convenience wrapper around {@link java.lang.System}
 * <br/>
 * TODO Should this be an interface?
 * @author David Ainslie
 *
 */
public final class Sys
{
    /** Environment given via system property, i.e what should the environment be? Defaults to "test" environment if not given. */
    public static final String ENVIRONMENT = System.getProperty("environment", "test");

    /** */
    public static final String NEW_LINE = System.getProperty("line.separator");

    /**
     * Utility.
     */
    private Sys()
    {
        super();
    }
}