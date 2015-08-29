package com.kissthinker.coder;

/**
 * @author David Ainslie
 *
 */
public interface Coder
{
    /**
     *
     * @param object Object to encode to an array of bytes
     * @param <O> Object type
     * @return Array of encoded bytes
     */
    <O> byte[] encode(O object);

    /**
     *
     * @param bytes Array of bytes to be decoded
     * @param <O> Object type
     * @return Object from decoded bytes
     */
    <O> O decode(byte[] bytes);
}