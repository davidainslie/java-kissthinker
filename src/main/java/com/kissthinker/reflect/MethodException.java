package com.kissthinker.reflect;

/**
 * @author David Ainslie
 *
 */
public class MethodException extends RuntimeException
{
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    public MethodException()
    {
        super();
    }
    
    /**
     *
     * @param message Message
     */
    public MethodException(String message)
    {
        super(message);
    }

    /**
     *
     * @param cause Cause
     */
    public MethodException(Throwable cause)
    {
        super(cause);
    }

    /**
     *
     * @param message Message
     * @param cause Cause
     */
    public MethodException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
