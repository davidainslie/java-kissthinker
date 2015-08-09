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

    /** */
    public boolean isThrowable()
    {
        return throwable != null;
    }

    /**
     * 
     * @param result
     * @return
     */
    public MethodInvocationWrapper result(Object result)
    {
        this.result = result;
        return this;
    }

    /** */
    public Object result()
    {
        return result;
    }

    /**
     * 
     * @param throwable
     * @return
     */
    public MethodInvocationWrapper throwable(Throwable throwable)
    {
        this.throwable = throwable;
        return this;
    }

    /** */
    public Throwable throwable()
    {
        return throwable;
    }
}