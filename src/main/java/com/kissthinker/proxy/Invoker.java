package com.kissthinker.proxy;

/**
 * @author David Ainslie
 *
 */
public interface Invoker
{
    /**
     *
     * @param <O> Object type
     * @param contractClass contract class
     * @param contractMethodName Contract method name
     * @param contractMethodArgs Contract method arguments
     * @return O
     */
    <O> O invoke(Class<?> contractClass, String contractMethodName, Object... contractMethodArgs);
}