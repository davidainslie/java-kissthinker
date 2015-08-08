package com.kissthinker.core.proxy;

/**
 * @author David Ainslie
 *
 */
public interface Invoker
{
    /**
     *
     * @param <O>
     * @param contract/class
     * @param contractMethodName
     * @param contractMethodArgs
     * @return O
     */
    <O> O invoke(Class<?> contractClass, String contractMethodName, Object... contractMethodArgs);
}