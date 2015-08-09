package com.kissthinker.coder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.cedarsoftware.util.io.JsonReader.jsonToJava;
import static com.cedarsoftware.util.io.JsonWriter.formatJson;
import static com.cedarsoftware.util.io.JsonWriter.objectToJson;

/**
 * Coding between Object and bytes via json-io.
 * @author David Ainslie
 *
 */
public class JsonCoder implements Coder
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonCoder.class);

    /** */
    public JsonCoder()
    {
        super();
    }

    /**
     * @see com.kissthinker.coder.Coder#decode(byte[])
     */
    @SuppressWarnings("unchecked")
    public <O> O decode(byte[] bytes)
    {
        return (O)jsonToJava(new String(bytes));
    }

    /**
     * @see com.kissthinker.coder.Coder#encode(java.lang.Object)
     */
    public <O> byte[] encode(O object) {
        String jsonObject = objectToJson(object);
        LOGGER.trace(jsonObject);

        return jsonObject.getBytes();
    }

    /**
     *
     * @param <O>
     * @param object
     * @return byte[]
     */
    public <O> byte[] prettyEncode(O object) {
        String jsonObject = formatJson(objectToJson(object));
        LOGGER.trace(jsonObject);

        return jsonObject.getBytes();
    }
}