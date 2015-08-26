package com.kissthinker.proxy;

/**
 * @author David Ainslie
 *
 */
public class SubscribeInvocation extends Invocation
{
    /**
     * @param targetClass Target class
     * @param returnClass Return class
     * @param methodName Method name
     * @param methodArgs Method arguments
     */
    public SubscribeInvocation(Class<?> targetClass, Class<?> returnClass, String methodName, Object... methodArgs)
    {
        super(targetClass, returnClass, methodName, methodArgs);
        // TODO Auto-generated constructor stub
    }
}