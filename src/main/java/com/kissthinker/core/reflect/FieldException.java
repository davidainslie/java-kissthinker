package com.kissthinker.core.reflect;

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
     * @param message
     */
    public FieldException(String message)
    {
        super(message);
    }

    /**
     *
     * @param cause
     */
    public FieldException(Throwable cause)
    {
        super(cause);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public FieldException(String message, Throwable cause)
    {
        super(message, cause);
    }
}