package com.kissthinker.coder;

import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

/**
 * Coding between Object and bytes via Jackson.
 * <p>
 * Note that Jackson has a XmlMapper:
 * <pre>
 * XmlMapper mapper = new XmlMapper();
 * MyBean bean = new MyBean();
 * String xml = mapper.writeValueAsString(bean);
 * // we get something like "<MyBean><property>value</property>....</MyBean>"
 * MyBean beanFromXml = mapper.readValue(xml, MyBean.class);
 * </pre>
 * @author David Ainslie
 *
 */
public class JacksonCoder implements Coder
{
    /** */
    private final ObjectMapper objectMapper;

    /** */
    private final ObjectWriter prettyObjectWriter;

    /**
     *
     */
    public JacksonCoder()
    {
        super();

        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);

        prettyObjectWriter = objectMapper.writerWithDefaultPrettyPrinter();
    }

    /**
     * @see com.kissthinker.coder.Coder#decode(byte[])
     */
    public <O> O decode(byte[] bytes)
    {
        /*
        User user = objectMapper.readValue(new File("c:\\user.json"), User.class);*/

        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.kissthinker.coder.Coder#encode(java.lang.Object)
     */
    public <O> byte[] encode(O object)
    {
        try
        {
            return objectMapper.writeValueAsBytes(object);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param <O> Object type
     * @param object Object
     * @return byte[]
     */
    public <O> byte[] prettyEncode(O object)
    {
        try
        {
            return prettyObjectWriter.writeValueAsBytes(object);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}