package com.kissthinker.javabean;

/**
 * Unless an AspectJ plugin is configured, IDEs such as Intellij will most likely highlight this class with an error.
 * Don't worry, there is no error as can be seen by running "mvn test".
 * Any class declares itself as a com.kissthinker.javabean.Bean needs a "propertyChangeSupport" - this is actually "mixed in" via AspectJ, but an IDE won't know that.
 * @author David Ainslie
 *
 */
public class SubBean extends Bean
{
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    @Property
    private String extraProperty;

    /** */
    public SubBean()
    {
        super();
    }
}