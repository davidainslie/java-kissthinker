package com.kissthinker.core.coder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author David Ainslie
 *
 */
public class GsonCoder implements Coder
{
    /** */
    private final Gson gson = new Gson();

    /** */
    private Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

    /**
     *
     */
    public GsonCoder()
    {
        super();
    }

    /**
     * @see com.kissthinker.core.coder.Coder#decode(byte[])
     */
    public <O> O decode(byte[] bytes)
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    /**
     * @see com.kissthinker.core.coder.Coder#encode(java.lang.Object)
     */
    public <O> byte[] encode(O object)
    {
        return gson.toJson(object).getBytes();
    }

    /**
     *
     * @param <O>
     * @param object
     * @return byte[]
     */
    public <O> byte[] prettyEncode(O object)
    {
        return prettyGson.toJson(object).getBytes();
    }
}