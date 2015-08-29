package com.kissthinker.reflect;

/**
 * @author David Ainslie
 */
public class MethodInvocationWrapper
{
    /** */
    private Object result;

    /** */
    private Throwable throwable;

    /**
     *
     * @return boolean Is it throwable
     */
    public boolean isThrowable()
    {
        return throwable != null;
    }

    /**
     * 
     * @param result Result
     * @return Method invocation wrapper
     */
    public MethodInvocationWrapper result(Object result)
    {
        this.result = result;
        return this;
    }

    /**
     *
     * @return Object The result
     */
    public Object result()
    {
        return result;
    }

    /**
     * 
     * @param throwable Throwable
     * @return Method invocation wrapper
     */
    public MethodInvocationWrapper throwable(Throwable throwable)
    {
        this.throwable = throwable;
        return this;
    }

    /**
     *
     * @return Throwable The Throwable
     */
    public Throwable throwable()
    {
        return throwable;
    }
}