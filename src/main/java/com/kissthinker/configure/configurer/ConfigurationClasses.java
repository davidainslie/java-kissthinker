package com.kissthinker.configure.configurer;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javassist.Modifier;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kissthinker.configure.Configurable;
import com.kissthinker.configure.Configuration;
import com.kissthinker.configure.Configurations;
import com.kissthinker.object.Singleton;
import com.kissthinker.proxy.Proxy;
import com.kissthinker.system.Environment;
import com.kissthinker.system.Sys;
import com.kissthinker.text.StringUtil;
import static com.kissthinker.collection.list.ListUtil.arrayList;
import static com.kissthinker.collection.map.MapUtil.hashMap;
import static com.kissthinker.collection.set.SetUtil.hashSet;

/**
 * Utility/Manager of "configuration classes".
 * <p>
 * Configurations are searched for on the classpath for classes marked up with {@link Configuration}
 * @author David Ainslie
 *
 */
@Singleton
public final class ConfigurationClasses
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationClasses.class);

    /** TODO System property inject or properites files? */
    private static final List<String> CONFIGURABLE_PACKAGES = arrayList("com.kissthinker");

    /** */
    private static final Map<Class<?>, List<Configuration>> CONFIGURATION_CLASSES = hashMap();

    /** */
    private static final Map<String, Class<?>> CONFIGURATION_CLASSES_PER_ID = hashMap();

    /**
     * Class initialisation.
     */
    static
    {
        LOGGER.info("Scanning for all available configurations...");

        if (System.getProperty("configurable.packages") != null)
        {
            Collections.addAll(CONFIGURABLE_PACKAGES, System.getProperty("configurable.packages").split(","));
        }

        Set<Class<?>> configurableClasses = hashSet();
        Set<Class<?>> configurationClasses = hashSet();
        Set<Class<?>> configurationsClasses = hashSet();

        for (String aPackage : CONFIGURABLE_PACKAGES)
        {
            Reflections reflections = new Reflections(aPackage);
            configurableClasses.addAll(reflections.getTypesAnnotatedWith(Configurable.class));
            configurationClasses.addAll(reflections.getTypesAnnotatedWith(Configuration.class));
            configurationsClasses.addAll(reflections.getTypesAnnotatedWith(Configurations.class));
        }

        for (Class<?> configurationClass : configurationClasses)
        {
            Configuration configuration = configurationClass.getAnnotation(Configuration.class);

            if (configuration != null) add(configurationClass, configuration);
        }

        for (Class<?> configurationsClass : configurationsClasses)
        {
            Configurations configurations = configurationsClass.getAnnotation(Configurations.class);

            for (Configuration configuration : configurations.value())
            {
                if (configuration != null) add(configurationsClass, configuration);
            }
        }

        LOGGER.info(StringUtil.toString("\n\nConfigurables (i.e. classes eligible for configuration)",
                                          "\n-------------------------------------------------------\n",
                                             configurableClasses,
                                        "\n\nAvailable Configurations",
                                          "\n------------------------\n",
                                             CONFIGURATION_CLASSES,
                                             Sys.NEW_LINE));
    }

    /**
     *
     * @param configurationID a unique identifier
     * @return Class<?> an implementation for the given configurationID
     */
    public static Class<?> lookup(String configurationID)
    {
        LOGGER.trace("Looking up a configuration for given unique configuration ID {}", configurationID);

        return CONFIGURATION_CLASSES_PER_ID.get(configurationID);
    }
    
    /**
     *
     * @param targetField
     * @return
     */
    public static Class<?> lookup(Field targetField)
    {
        LOGGER.trace("Looking up a configuration for given field {}", targetField);

        // Lookup the scanned in configurations.
        Configuration matchingConfiguration = null;
        Class<?> matchingConfigurationClass = null;

        for (Class<?> configurationClass : CONFIGURATION_CLASSES.keySet())
        {
            if (targetField.getType().isAssignableFrom(configurationClass))
            {
                List<Configuration> configurations = CONFIGURATION_CLASSES.get(configurationClass);

                for (Configuration configuration : configurations)
                {
                    if (matchingConfiguration == null)
                    {
                        matchingConfiguration = configuration;
                        matchingConfigurationClass = configurationClass;

                        if (configuration.isDefault())
                        {
                            break;
                        }
                    }
                    // TODO Not happy with the following:
                    else if (Environment.highestOrdinal(configuration.environment())
                             > Environment.highestOrdinal(matchingConfiguration.environment()))
                    {
                        matchingConfiguration = configuration;
                        matchingConfigurationClass = configurationClass;
                    }
                    else if (matchingConfigurationClass.isAssignableFrom(configurationClass))
                    {
                        matchingConfiguration = configuration;
                        matchingConfigurationClass = configurationClass;
                    }
                }
            }
        }

        if (matchingConfiguration != null)
        {
            if (matchingConfiguration.proxy().equals(Proxy.Null.class))
            {
                return matchingConfigurationClass;
            }

            return matchingConfiguration.proxy();
        }

        return null;
    }

    /**
     * TODO Probably remove this as only referenced by a unit test.
     * @return Set<Class<?>>
     */
    public static Set<Class<?>> configurationClasses()
    {
        return Collections.unmodifiableSet(CONFIGURATION_CLASSES.keySet());
    }

    /**
     *
     * @param configurationClass
     * @param configuration
     */
    private static void add(Class<?> configurationClass, Configuration configuration)
    {
        // Filter out configurations by environment.
        if (Environment.isRutime(configuration.environment()))
        {
            Class<? extends Proxy> proxyClass = configuration.proxy();

            if (proxyClass.equals(Proxy.Null.class))
            {
                if (configurationClass.isInterface() || Modifier.isAbstract(configurationClass.getModifiers()))
                {
                    // Given configuration class cannot be instantiated so it is not a viable configuration/implementation.
                    return;
                }
            }
            else if (proxyClass.isInterface() || Modifier.isAbstract(proxyClass.getModifiers()))
            {
                // Given proxy configuration class cannot be instantiated so it is not a viable configuration/implementation.
                return;
            }

            // Viable configuration/implementation.
            List<Configuration> configurations = CONFIGURATION_CLASSES.get(configurationClass);

            if (configurations == null)
            {
                configurations = arrayList();
                CONFIGURATION_CLASSES.put(configurationClass, configurations);
            }

            if (configuration.isDefault())
            {
                configurations.add(0, configuration);
            }
            else
            {
                configurations.add(configuration);
            }

            if (!"".equals(configuration.id()))
            {
                CONFIGURATION_CLASSES_PER_ID.put(configuration.id(), proxyClass.equals(Proxy.Null.class) ? configurationClass : proxyClass);
            }
        }
    }

    /**
     * Utility.
     */
    private ConfigurationClasses()
    {
        super();
    }
}