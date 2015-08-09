package com.kissthinker.coder;

import net.minidev.json.JSONValue;

/**
 * 
 * <br/>
 * @author David Ainslie
 *
 */
public class JsonSmartCoder implements Coder
{
    /** */
    public JsonSmartCoder()
    {
        super();
    }

    /**
     * @see com.kissthinker.coder.Coder#decode(byte[])
     */
    @SuppressWarnings("unchecked")
    public <O> O decode(byte[] bytes)
    {
        return (O)JSONValue.parse(bytes);
    }

    /**
     * @see com.kissthinker.coder.Coder#encode(java.lang.Object)
     */

    public <O> byte[] encode(O object)
    {
        return JSONValue.toJSONString(object).getBytes();
    }
}