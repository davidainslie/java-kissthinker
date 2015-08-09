package com.kissthinker.coder;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;

/**
 * @author David Ainslie
 *
 */
public class XMLBeanCoder implements Coder
{
    /** */
    public XMLBeanCoder()
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
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        XMLEncoder xmlEncoder = new XMLEncoder(byteArrayOutputStream);
        xmlEncoder.writeObject(object);
        xmlEncoder.close();

        return byteArrayOutputStream.toByteArray();
    }
}