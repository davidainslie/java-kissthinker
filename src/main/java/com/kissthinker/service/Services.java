package com.kissthinker.service;

import java.util.*;
import java.util.Map.Entry;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kissthinker.factory.Factory;
import com.kissthinker.object.Singleton;
import com.kissthinker.system.Environment;
import com.kissthinker.system.Sys;
import com.kissthinker.text.StringUtil;
import static com.kissthinker.collection.list.ListUtil.arrayList;
import static com.kissthinker.collection.map.MapUtil.hashMap;
import static com.kissthinker.collection.set.SetUtil.hashSet;

/**
 * Utility/Manager of "services".
 *
 * Services are started (automatically) by searching the classpath for classes marked up with {@link Service}
 * @author David Ainslie
 *
 */
@Singleton
public final class Services
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(Services.class);

    /** Packages to look up "services", set via -Dconfigurable.packages (a comma delimited list) */
    private static final List<String> SERVICE_PACKAGES = arrayList("com.kissthinker");

    /** */
    private static final Set<Class<?>> SERVICE_CLASSES = hashSet();

    /** */
    private static final Map<Class<?>, Object> SERVICE_SINGLETONS = hashMap();

    /** */
    private static final Map<Class<?>, List<Object>> SERVICE_POOL = hashMap();

    /**
     * Class initialisation.
     */
    static
    {
        LOGGER.info("Starting services...");

        // TODO Allow for properites files as well as -D?
        if (System.getProperty("configurable.packages") != null)
        {
            Collections.addAll(SERVICE_PACKAGES, System.getProperty("configurable.packages").split(","));
        }

        Set<Class<?>> serviceClasses = hashSet();

        for (String aPackage : SERVICE_PACKAGES)
        {
            Reflections reflections = new Reflections(aPackage);
            serviceClasses.addAll(reflections.getTypesAnnotatedWith(Service.class));
        }

        for (Class<?> serviceClass : serviceClasses)
        {
            Service service = serviceClass.getAnnotation(Service.class);

            // Filter out services by environment.
            if (Environment.isRutime(service.environment()))
            {
                SERVICE_CLASSES.add(serviceClass);

                if (Factory.isSingleton(serviceClass))
                {
                    SERVICE_SINGLETONS.put(serviceClass, Factory.create(serviceClass));
                }
                else
                {
                    List<Object> pooledService = new ArrayList<>();
                    SERVICE_POOL.put(serviceClass, pooledService);

                    // TODO Configure pool size.
                    for (int i = 0; i < 5; i++)
                    {
                        pooledService.add(Factory.create(serviceClass));
                    }
                }
            }
        }

        LOGGER.info(StringUtil.toString("\n\nAvailable Services",
                                          "\n------------------\n",
                                             SERVICE_CLASSES,
                                             Sys.NEW_LINE));
    }

    /**
     * Convenience method instead of calling Class.forName("com.kissthinker.service.Services");
     */
    public static void start()
    {
        try
        {
            Class.forName(Services.class.getName());
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param <S> Service class type
     * @param serviceClass Service type
     * @return Service
     */
    @SuppressWarnings("unchecked")
    public static <S> S get(Class<? extends S> serviceClass)
    {
        LOGGER.debug("Getting service for given class {}", serviceClass.getName());

        // Get best match from set TODO
        for (Entry<Class<?>, Object> singleton : SERVICE_SINGLETONS.entrySet())
        {
            if (singleton.getKey().isAssignableFrom(serviceClass))
            {
                return (S)singleton.getValue();
            }
        }

        for (Entry<Class<?>, List<Object>> servicePool : SERVICE_POOL.entrySet())
        {
            if (serviceClass.isAssignableFrom(servicePool.getKey()))
            {
                return (S)servicePool.getValue().get(0); // TODO
            }
        }

        return null;
    }

    /**
     * Utility.
     */
    private Services()
    {
        super();
    }
}