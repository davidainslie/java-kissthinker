package com.kissthinker.coder;

/**
 * A {@link Coder} for the publish/subscribe paradigm.
 * <br/>
 * This {@link Coder} is specifically for interactions via {@link Publisher} which uses, as part of its "subscribe" methods, {@link Function} for callbacks.<br/>
 * Appropriate implementation regarding pub/sub is JMS.
 * @author David Ainslie
 *
 */
@Deprecated // TODO Only at the moment because I want to implement JMS and had an idea with this and class JMSRegistry, but the idea has currently left me.
public class JMSCoder extends JsonCoder
{
    /** */
    public JMSCoder()
    {
        super();
    }

    /**
     * @see com.kissthinker.coder.JsonCoder#decode(byte[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public <O> O decode(byte[] bytes)
    {
        // TODO Auto-generated method stub
        return (O)super.decode(bytes);
    }

    /**
     * @see com.kissthinker.coder.JsonCoder#encode(java.lang.Object)
     */
    @Override
    public <O> byte[] encode(O object)
    {
        // TODO Auto-generated method stub
        return super.encode(object);
    }
}