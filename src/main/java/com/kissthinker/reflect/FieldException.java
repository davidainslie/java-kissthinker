package com.kissthinker.reflect;

/**
 * @author David Ainslie
 *
 */
public class FieldException extends RuntimeException
{
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    public FieldException()
    {
        super();
    }
    
    /**
     *
     * @param message Message
     */
    public FieldException(String message)
    {
        super(message);
    }

    /**
     *
     * @param cause Cause
     */
    public FieldException(Throwable cause)
    {
        super(cause);
    }

    /**
     *
     * @param message Message
     * @param cause Cause
     */
    public FieldException(String message, Throwable cause)
    {
        super(message, cause);
    }
}