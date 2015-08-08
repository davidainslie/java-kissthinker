package com.kissthinker.core.coder;

import com.alibaba.fastjson.JSON;

/**
 * Mmmm, seems to want javabean methods.
 * <br/>
 * @author David Ainslie
 *
 */
public class FastJsonCoder implements Coder
{
    /** */
    public FastJsonCoder()
    {
        super();
    }

    /**
     * @see com.kissthinker.core.coder.Coder#decode(byte[])
     */
    @SuppressWarnings("unchecked")
    public <O> O decode(byte[] bytes)
    {
        return (O)JSON.parse(bytes);
    }

    /**
     * @see com.kissthinker.core.coder.Coder#encode(java.lang.Object)
     */
    public <O> byte[] encode(O object)
    {
        return JSON.toJSONBytes(object);
    }

    /**
     *
     * @param <O>
     * @param object
     * @return byte[]
     */
    public <O> byte[] prettyEncode(O object)
    {
        return JSON.toJSONString(object, true).getBytes();
    }
}