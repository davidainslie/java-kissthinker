package com.kissthinker.coder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author David Ainslie
 *
 */
public class SerializationCoder implements Coder
{
    /** */
    public SerializationCoder()
    {
        super();
    }

    /**
     * @see com.kissthinker.coder.Coder#decode(byte[])
     */
    @Override
    public <O> O decode(byte[] bytes)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.kissthinker.coder.Coder#encode(java.lang.Object)
     */
    @Override
    public <O> byte[] encode(O object)
    {
        try
        {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();

            return byteArrayOutputStream.toByteArray();
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException(e);
        }
    }
}