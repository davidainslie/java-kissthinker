package com.kissthinker.coder;

import java.io.*;

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
        try
        {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            @SuppressWarnings("unchecked")
            O o = (O)objectInputStream.readObject();

            objectInputStream.close();

            return o;
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new IllegalArgumentException(e);
        }
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