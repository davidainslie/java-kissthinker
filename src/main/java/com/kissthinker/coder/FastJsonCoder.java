package com.kissthinker.coder;

import com.alibaba.fastjson.JSON;

/**
 * Mmmm, seems to want javabean methods - So this one really doesn't work yet!
 * <p>
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
     * @see com.kissthinker.coder.Coder#decode(byte[])
     */
    @SuppressWarnings("unchecked")
    public <O> O decode(byte[] bytes)
    {
        return (O)JSON.parse(bytes);
    }

    /**
     * @see com.kissthinker.coder.Coder#encode(java.lang.Object)
     */
    public <O> byte[] encode(O object)
    {
        return JSON.toJSONBytes(object);
    }

    /**
     *
     * @param <O> Object type
     * @param object Object
     * @return byte[]
     */
    public <O> byte[] prettyEncode(O object)
    {
        return JSON.toJSONString(object, true).getBytes();
    }
}