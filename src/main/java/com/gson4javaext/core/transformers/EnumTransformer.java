package com.gson4javaext.core.transformers;

import java.lang.reflect.Method;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.gson4javaext.core.Java2JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enum transformer to generate JSON from Object and vice versa.
 * <br/>
 * Note that the above author (noellynch) originally created this file, but was essentially empty i.e did nothing and was not used anyway.<br/>
 * A tad odd, as I, David Ainslie, originally downloaded GSON4JavaExt, http://code.google.com/p/gson4javaext/, as it claimed to extend GSON to handle enums.<br/>
 * Alas, even though the website claimed this, at the time of download, the implementation to handle enums was missing.<br/>
 * So I've implemented this class and had to update {@link Java2JSON} to use this class.
 * @author David Ainslie
 *
 */
public class EnumTransformer extends TransformerBase<Enum<?>>
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(EnumTransformer.class);

    /**
     *
     * @see com.gson4javaext.core.transformers.TransformerBase#fromJSON(JsonElement)
     */
	@Override
	public Enum<?> fromJSON(JsonElement jsonElement)
	{
	    try
        {
            JsonObject jsonObject = (JsonObject)jsonElement;

            String className = jsonObject.get("class").toString().replaceAll("\"", "");
            String enumValue = jsonObject.get("enum").toString().replaceAll("\"", "");

            Class<?> enumClass = loadClass(className);

            Method valueOfMethod = Enum.class.getDeclaredMethod("valueOf", Class.class, String.class);

            return (Enum<?>)valueOfMethod.invoke(null, enumClass, enumValue);
        }
        catch (Exception e)
        {
            LOGGER.error("Just returning a null, for failing to convert to an enum, the given JsonElement: " + jsonElement, e);
            return null;
        }
	}

	/**
	 *
	 * @see com.gson4javaext.core.transformers.TransformerBase#toJSON(Object)
	 */
	@Override
	public	JsonElement	toJSON(Enum<?> anEnum)
	{
	    JsonObject jsonObject = new JsonObject();
	    jsonObject.addProperty("enum", anEnum.name());
	    jsonObject.addProperty("class", anEnum.getClass().getName());

        return jsonObject;
	}

	/**
	 * A slightly paranoid version of loading a class.<br/>
	 * Why paranoid? When getting Strings from {@link JsonObject}, you don't just get a String, you also get the quotes surrounding a String.
	 * @param pathAndClassName
	 * @return Class<?>
	 * @throws ClassNotFoundException
	 */
	private Class<?> loadClass(String pathAndClassName) throws ClassNotFoundException
    {
        pathAndClassName = pathAndClassName.replaceAll("\"", "").replaceAll("\'", "");

        try
        {
            return (Class<?>)Thread.currentThread().getContextClassLoader().loadClass(pathAndClassName);
        }
        catch (Exception e)
        {
            return (Class<?>)Class.forName(pathAndClassName, true, Thread.currentThread().getContextClassLoader());
        }
    }
}