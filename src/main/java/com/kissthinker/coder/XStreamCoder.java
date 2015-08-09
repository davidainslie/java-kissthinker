package com.kissthinker.coder;

import com.thoughtworks.xstream.XStream;

/**
 * @author David Ainslie
 *
 */
public class XStreamCoder implements Coder
{
    /** */
    private final XStream xstream = new XStream();

    /** */
    public XStreamCoder()
    {
        super();
    }

    /**
     * @see com.kissthinker.coder.Coder#decode(byte[])
     */
    public <O> O decode(byte[] bytes)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.kissthinker.coder.Coder#encode(java.lang.Object)
     */
    public <O> byte[] encode(O object)
    {
        return xstream.toXML(object).getBytes();
    }
}