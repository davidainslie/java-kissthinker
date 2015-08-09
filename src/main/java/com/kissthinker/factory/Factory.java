package com.kissthinker.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kissthinker.collection.list.ListUtil;
import com.kissthinker.configure.Configuration;
import com.kissthinker.configure.Configurations;
import com.kissthinker.object.Singleton;
import com.kissthinker.proxy.Proxy;
import static com.kissthinker.collection.map.MapUtil.hashMap;
import static java.text.MessageFormat.format;

/**
 * Factory to be used as a generic factory to instantiate any class i.e avoids coding factories per class.
 * <br/>
 * This factory also provides "life cycle management".
 * @author David Ainslie
 *
 */
public final class Factory
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(Factory.class);

    /** Cached singletons. Any classes marked with @{@link Singleton} are stored here and so only instantiated once. */
    private static final Map<Class<?>, Object> SINGLETONS = hashMap();

    /** */
    private static final Lock SINGLETONS_LOCK = new ReentrantLock();

    /**
     * Create/Instantiate a given class.<br/>
     * This way of instantiation has many benefits.
     * <ul>
     *  <li>This is the only factory (for object instantiation) that you will need i.e don't have to code a factory per object type.</li>
     *  <li>Objects can be managed as singletons when annotated with {@link Singleton}.</li>
     *  <li>This is a TODO but object life cycle management can be handled here, in one place (life cycle interfaces could be coded).</li>
     *  <li>As a "fallback" an object can be instantiated even if there is no appropriate constructor.</li>
     * </ul>
     * Note the unnecessary casting to O. This is not required by Eclipse, but required by Maven.
     * @param <O>
     * @param class_
     * @param constructorParamters
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <O> O create(Class<?> class_, Object... constructorParamters)
    {
        // Given class is a singleton?
        O singleton = (O)SINGLETONS.get(class_);

        if (singleton != null)
        {
            LOGGER.debug("Returning singleton {} for given class {}", singleton.getClass().getName(), class_.getName());
            return singleton;
        }

        if (class_.isAnnotationPresent(Singleton.class))
        {
            try
            {
                SINGLETONS_LOCK.lock();

                singleton = (O)SINGLETONS.get(class_);

                if (singleton != null)
                {
                    LOGGER.debug("Returning singleton {} for given class {}", singleton.getClass().getName(), class_.getName());
                    return singleton;
                }

                singleton = (O)createInstance(class_, constructorParamters);

                if (singleton != null)
                {
                    SINGLETONS.put(class_, singleton);
                }

                return singleton;
            }
            finally
            {
                SINGLETONS_LOCK.unlock();
            }
        }
        else
        {
            return (O)createInstance(class_, constructorParamters);
        }
    }

    /**
     * Convenience alternative to {@link #create(Class)}.<br/>
     * Note the unnecessary casting to O. This is not required by Eclipse, but required by Maven.
     * @param <O>
     * @param className String of complete class name such as java.lang.String
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <O> O create(String className) throws IllegalArgumentException
    {
        try
        {
            return (O)create(Class.forName(className));
        }
        catch (ClassNotFoundException e)
        {
            throw new IllegalArgumentException(format("Given class name {0} cannot be instantiated", className), e);
        }
    }

    /**
     *
     * @param <O>
     * @param class_
     * @return
     */
    public static <O> boolean isSingleton(Class<?> class_)
    {
        return class_.isAnnotationPresent(Singleton.class);
    }

    /**
     *
     * @param <O>
     * @param class_
     * @param constructorParamters
     * @return O
     */
    @SuppressWarnings("unchecked")
    private static <O> O createInstance(Class<?> class_, Object... constructorParamters)
    {
        Configuration configuration = class_.getAnnotation(Configuration.class);

        // TODO The following was added when Configurations annotation was added - but I don't like this implementation.
        if (configuration == null)
        {
            Configurations configurations = class_.getAnnotation(Configurations.class);

            if (configurations != null)
            {
                for (Configuration aConfiguration : configurations.value())
                {
                    if (aConfiguration.isDefault())
                    {
                        configuration = aConfiguration;
                    }
                    else if (configuration == null)
                    {
                        configuration = aConfiguration;
                    }
                }
            }
        }

        if (configuration != null && !configuration.proxy().equals(Proxy.Null.class))
        {
            return (O)instantiate(configuration.proxy(), constructorParamters);
        }

        return (O)instantiate(class_, constructorParamters);
    }

    /**
     * Instantiate from a no-arg constructor (it could be private)
     * @param <O>
     * @param class_
     * @param constructorParamters
     * @return O
     */
    @SuppressWarnings("unchecked")
    private static <O> O instantiate(Class<?> class_, Object... constructorParamters)
    {
        if (class_.isInterface())
        {
            LOGGER.error("Attempted to instantiate interface {} - instead 'null' is being returned", class_.getName());
            return null;
        }

        if (Modifier.isAbstract(class_.getModifiers()))
        {
            LOGGER.error("Attempted to instantiate abstract class {} - instead 'null' is being returned", class_.getName());
            return null;
        }

        // Standard Java way of looking for appropriate constructor.
        try
        {
            Constructor<?> constructor = class_.getConstructor(classes(constructorParamters));

            if (constructor != null)
            {
                if (constructorParamters == null)
                {
                    return (O)constructor.newInstance();
                }

                return (O)constructor.newInstance(constructorParamters);
            }

            LOGGER.error("Unable to instantiate {} via no arg constructor as it could not be found", class_.getName());
        }
        catch (Exception e)
        {
            LOGGER.error("Object instantiation failure for " + class_.getName(), e);
            throw new IllegalArgumentException(e);
        }

        // Let's try the Objenesis way, which bypasses constructors
        // NOTE Isn't this dangerous e.g. only 1 constructor with 1 argument that does some initialisation.
        LOGGER.warn("As standard Java instantiation of {} via a no arg constructor failed, Objenesis will be used to instantiate by bypassing constructor", class_.getName());
        Objenesis objenesis = new ObjenesisStd(); // or ObjenesisSerializer
        return (O)objenesis.newInstance(class_);
    }

    /**
     *
     * @param objects
     * @return Class<?>[]
     */
    private static Class<?>[] classes(Object... objects)
    {
        if (objects == null)
        {
            return null;
        }

        List<Class<?>> classes = ListUtil.arrayList();

        for (Object object : objects)
        {
            classes.add(object.getClass());
        }

        return classes.toArray(new Class<?>[classes.size()]);
    }

    /**
     * Utility.
     */
    private Factory()
    {
        super();
    }
}