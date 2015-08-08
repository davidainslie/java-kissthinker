package com.kissthinker.core.proxy;

/**
 * @author David Ainslie
 *
 */
public class SubscribeInvocation extends Invocation
{
    /**
     * @param targetClass
     * @param returnClass
     * @param methodName
     * @param methodArgs
     */
    public SubscribeInvocation(Class<?> targetClass, Class<?> returnClass, String methodName, Object... methodArgs)
    {
        super(targetClass, returnClass, methodName, methodArgs);
        // TODO Auto-generated constructor stub
    }
}