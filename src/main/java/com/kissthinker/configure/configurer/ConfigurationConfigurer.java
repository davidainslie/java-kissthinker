package com.kissthinker.configure.configurer;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import com.kissthinker.configure.Configuration;
import com.kissthinker.configure.Configure;
import com.kissthinker.object.ClassUtil;
import com.kissthinker.system.Environment;
import static com.kissthinker.collection.map.MapUtil.hashMap;
import static com.kissthinker.object.ObjectUtil.morph;

/**
 * Look for "configurations" to be "injected" into fields annotated with {@link Configure} by searching the classpath for implementations
 * whether interfaces, abstract classes or concrete classes that have the type annotation {@link Configuration}
 * <br/>
 * @author David Ainslie
 *
 */
public class ConfigurationConfigurer extends Configurer
{
    /** */
    private static final ThreadLocal<Map<Class<?>, Properties>> CONFIGURATION_PROPERTIES = new ThreadLocal<Map<Class<?>, Properties>>()
    {
        /**
         * @see java.lang.ThreadLocal#initialValue()
         */
        @Override
        protected Map<Class<?>, Properties> initialValue()
        {
            return hashMap();
        }
    };

    /**
     *
     * @param configurator
     */
    public ConfigurationConfigurer(Configurator configurator)
    {
        super(configurator);
    }

    /**
     * TODO Lots of ifs - candidate for refactoring (maybe a rules example)
     * @see com.kissthinker.configure.configurer.Configurer#doConfigure(java.lang.Object, java.lang.Class, java.lang.reflect.Field)
     */
    @Override
    protected boolean doConfigure(Object configurable, Class<? extends Object> configurableClass, Field field)
    {
        Configure configure = field.getAnnotation(Configure.class);
        String propertyKey = configure.id();
        String propertyValue = null;

        // System property by ID?
        if (propertyKey != null && !"".equals(propertyKey = propertyKey.trim()))
        {
            // System property by ID?
            propertyValue = System.getProperty(propertyKey);

            if (propertyValue == null || "".equals(propertyValue = propertyValue.trim()))
            {
                // Configuration property by ID?
                propertyValue = getProperty(propertyKey, configurableClass);
            }
        }

        if (propertyValue == null || "".equals(propertyValue = propertyValue.trim()))
        {
            // System property by field type?
            propertyValue = System.getProperty(field.getType().getName());
        }

        if (propertyValue == null || "".equals(propertyValue = propertyValue.trim()))
        {
            // Configuration property by field type?
            propertyValue = getProperty(field.getType().getName(), configurableClass);
        }

        if (propertyValue != null) {
            propertyValue.trim();

            // Is it to be found in ConfigurationClasses?
            Class<?> configurationClass = ConfigurationClasses.lookup(propertyValue);

            if (configurationClass != null)
            {
                return configure(configurable, field, configurationClass);
            }

            // Is it a class name?
            try
            {
                configurationClass = Class.forName(propertyValue);
                return configure(configurable, field, configurationClass);
            } catch (Exception e) {
                // Ignore.
            }

            // Is it a value? Try "morphing"?
            Object morph = morph(propertyValue, field.getType());

            if (morph != null)
            {
                return configure(configurable, field, morph);
            }
        }

        // Scanned in configuration class?
        Class<?> configurationClass = ConfigurationClasses.lookup(field);

        return configurationClass != null && configure(configurable, field, configurationClass);
    }

    /**
     * Use given key (for exampe a given Configure.id) to look up (possible) .properties
     * Configurate via "property" file the order of (where each one overrides the previous):
     * <ul>
     *  <li>/META-INF/configure/application.properties</li>
     *  <li>/META-INF/configure/application-ENV.properties</li>
     *  <li>/META-INF/configure/com/kissthinker/blah/BlahClass.properties</li>
     *  <li>/META-INF/configure/com/kissthinker/blah/BlahClass-ENV.properties</li>
     * </ul>
     * Note, that I thought about allowing "property" file lookups even without have "id" set on annotation {@link Configure} e.g<br/>
     * {@code @Configure(id="myIDGenerator") private final IDGenerator<?> idGenerator = null; }<br/>
     * The idea would be that the name of the field would be used (via reflection) to look up a property setting.<br/>
     * However, the issue with this is when refactoring, such as changing the name of the field - and one forgets to update that name in the properties file.
     * @param key
     * @param configurableClass
     * @return
     */
    private String getProperty(String key, Class<? extends Object> configurableClass)
    {
        try
        {
            Properties properties = CONFIGURATION_PROPERTIES.get().get(configurableClass);

            if (properties == null)
            {
                properties = new Properties();
                CONFIGURATION_PROPERTIES.get().put(configurableClass, properties);

                // Look up ".properties" file for "application".
                InputStream inputStream = getClass().getResourceAsStream("/META-INF/configure/application.properties");

                if (inputStream != null)
                {
                    properties.load(inputStream);
                }

                // Look up ".properties" file for "application" within runtime environment - overridding any loaded properties.
                inputStream = getClass().getResourceAsStream("/META-INF/configure/application" + "-" + Environment.RUNTIME + ".properties");

                if (inputStream != null)
                {
                    properties.load(inputStream);
                }

                // Look up ".properties" file for given configurable - overridding any loaded properties.
                inputStream = getClass().getResourceAsStream(ClassUtil.pathAndClassName("/META-INF/configure/", configurableClass, ".properties"));

                if (inputStream != null)
                {
                    properties.load(inputStream);
                }

                // Look up ".properties" file for given configurable within runtime environment - overridding any loaded properties.
                inputStream = getClass().getResourceAsStream(ClassUtil.pathAndClassName("/META-INF/configure/", configurableClass, "-" + Environment.RUNTIME + ".properties"));

                if (inputStream != null)
                {
                    properties.load(inputStream);
                }
            }

            return properties.getProperty(key);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}