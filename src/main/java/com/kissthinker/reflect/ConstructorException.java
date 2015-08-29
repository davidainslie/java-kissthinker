package com.kissthinker.reflect;

/**
 * @author David Ainslie
 *
 */
public class ConstructorException extends RuntimeException
{
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    public ConstructorException()
    {
        super();
    }

    /**
     *
     * @param message Message
     */
    public ConstructorException(String message)
    {
        super(message);
    }

    /**
     *
     * @param cause Cause
     */
    public ConstructorException(Throwable cause)
    {
        super(cause);
    }

    /**
     *
     * @param message Message
     * @param cause Cause
     */
    public ConstructorException(String message, Throwable cause)
    {
        super(message, cause);
    }
}