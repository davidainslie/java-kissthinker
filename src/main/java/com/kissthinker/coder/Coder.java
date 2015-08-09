package com.kissthinker.coder;

/**
 * @author David Ainslie
 *
 */
public interface Coder
{
    /**
     *
     * @param object
     * @param <O>
     * @return
     */
    <O> byte[] encode(O object);

    /**
     *
     * @param bytes
     * @param <O>
     * @return
     */
    <O> O decode(byte[] bytes);
}