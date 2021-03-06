package com.kissthinker.configure.configurer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.FieldSignature;
import org.aspectj.lang.reflect.InitializerSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kissthinker.configure.Configurable;
import com.kissthinker.configure.Configure;
import com.kissthinker.factory.Factory;
import com.kissthinker.proxy.Proxy;
import com.kissthinker.reflect.ReflectUtil;
import com.kissthinker.system.Environment;
import static com.kissthinker.collection.array.ArrayUtil.va;
import static java.text.MessageFormat.format;

/**
 * Any class marked @{@link Configurable} has its fields that are marked @{@link Configure} set by this aspect.
 *
 * Any field, whether static or not and final or not can be configured.
 * Firstly, if a {@link Configure} has something other than "null", then it will not be configured.
 * e.g
 * <pre>
 * {@code @Configure} private Foo foo = new Foo();
 * </pre>
 * This variable will not be configured, essentially it is not eligible for being configured because an actual value has been stipulated.
 * This has been done for 2 reasons.
 * 1) It allows for normal/usual Java development or using "new" (whether instantiated by a factory or not does not matter).
 * 2) Allowing this is beneficial from a client developing perspective as, when a class is first developed, there may not be an appropriate configuration.
 * This may not actually be a valid reason, but a Developer will often just want to instantiate anything as a means to testing the initial (and ongoing) development.
 * Note that if "foo = null" in the declaration, it can be configured.
 * This is actually essential for "final" fields as a final field always has to be set to "something". By setting a final initially to null we keep the compiler happy.
 * At this point of "initialisation", note that a Developer is still allowed to set (final and non final) fields in class and instance initialisers.
 * Again, this allows for normal/usual Java development. However, this does go against the idea of using a "configuration framework", but at least the "framework" does not intefere.
 * So how is an application configured (say use xml, or scripting, or some custom way - this is allowed by this framework) is actually configurable.
 * If the sytem property "application.configurer" is provided and that "class name" declares an extension of {@link Configurer},
 * then configurations are performed by the instructions laid out by that implementation.
 * The default "configurer" is {@link ConfigurationConfigurer}
 * There are rules to the configuration process (mainly what is the order of getting an appropriate/desired configuration).
 * These rules are declared within a {@link Configurer}, whether those rules use helper methods in the abstract class {@link Configurer} that all configurers should extend
 * of whether custom rules are coded. Here follows some examples of rules.
 * One would hope that "id" is not provided, as Configurations is really about "I don't care, just configure me".
 * However if an "id" is provided, then it is assumed that the Developer want something specific and so this takes highest priority.
 * But also not, that if the sytem property "application.configurer" is provided, then any list of "Configurer" can be provided in any order, so overriding these defaults.
 * <pre>
 * configure.id()
 *  - system property
 *      - bean id to in Configurations
 *      - class name
 *      - value to morph
 *
 *  - META-INF/configure/application-env.properties
 *      - bean id to in Configurations
 *      - class name
 *      - value to morph
 *
 *  - META-INF/configure/application.properties
 *      - bean id to in Configurations
 *      - class name
 *      - value to morph
 *
 * ConfigurationsConfigurer, the default configurer
 *  - Configurations
 *
 * Field type
 *  - system property
 *      - class name
 *
 *  - META-INF/configure/packageAndClassType-env.properties  where class type is the class where the @Configure field is
 *      e.g. META-INF/configure/com/kisstrader/code/Trader-test.properties
 *       - bean id to in Configurations
 *       - class name
 *       - value to morph
 *
 *  - META-INF/configure/packageAndClassType.properties
 *      - bean id to in Configurations
 *      - class name
 *      - value to morph
 *
 * One would hope (just like "id") that "otherwise" is not provided, but this is a useful compromise especially during development (testing/mocking).
 *
 * otherwise
 *      - value to morph
 *
 * Other existing "configurers" that can be used:
 * {@link JavaBeanXMLConfigurer} - This uses Java's XMLDecoder to read a XML file that is a configuration.
 * {@link IoCConfigurer} - Equivalent to standard XML IoC but instead of XML, scripting is used to declare the configurations.
 * This Configurer utilises "Rhino", Java's embedded scripting language
 * Along the line of IocConfigurer, other "scripting" configurers could be created for the likes of Groovy, JRuby etc.
 * Note that any custom configurer can be developed by extending the abstract (base) class {@link Configurer}
 *
 * </pre>
 * AN IMPORTANT NOTE
 * Like any configuration, IoC, dependency injection framework (or however you wish to name this process), one must be careful of cyclic dependencies.
 * Example:
 * <pre>
 * Singleton
 * Configurable
 * Configuration
 * public class A
 * {
 *      Configure B b
 * }
 *
 * Configurable
 * Configuration
 * public class BImpl extends B
 * {
 *      Configure A a
 * }
 *
 * - the above would only work if at least one class is a singleton.
 * TODO If not, should detect this (I guess possible) infinite recursion and log it. Test it
 * </pre>
 * Final note about "otherwise" (which should actually be avoided like the use of "id" i.e should really only declare @Configure with no attributes)
 * Let's take the following example:
 * <pre>
 *{@code @Configure} int port = 1099;
 * This will never be configured
 * However if you do
 *{@code @Configure} Integer port = null;
 * or
 *{@code @Configure}(otherwise="1099") Integer port = null;
 * "port" is now eligible for configuration, and in the second case no configuration can be found we can still fallback on "otherwise".
 * Note we did not do:
 *{@code @Configure} int port;
 * as primitives are given a default if one is not specified (in this case defaulting to 0) would mean no configuration attempt as a value would have already been set.
 </pre>
 * @author David Ainslie
 *
 */
@Aspect
public class Configurator
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(Configurator.class);

    /** */
    private final Map<Class<?>, Map<Field, Object>> configuredClassFields = new HashMap<>();

    /** */
    private final Map<Object, Map<Field, Object>> configuredObjectFields = new HashMap<>();

    /** */
    private Configurer configurer;

    /** */
    public Configurator()
    {
        super();

        String applicationConfigurer = System.getProperty("application.configurer");

        try
        {
            if (applicationConfigurer != null && !"".equals(applicationConfigurer))
            {
                @SuppressWarnings("unchecked")
                Class<Configurer> configurerClass = (Class<Configurer>)Class.forName(applicationConfigurer);
                Constructor<Configurer> configurerConstructor = configurerClass.getConstructor(Configurator.class);

                configurer = configurerConstructor.newInstance(this);
            }
        }
        catch (Exception e)
        {
            LOGGER.warn(format("Failed to create 'configurer' {0}, so will use default", applicationConfigurer), e);
        }
        finally
        {
            if (configurer == null)
            {
                configurer = Factory.create(ConfigurationConfigurer.class, this);
            }

            LOGGER.debug("Using 'configurer' {}", configurer.getClass().getName());
        }
    }

    /** */
    @Pointcut("staticinitialization(*) && within(@com.kissthinker.configure.Configurable *)")
    public void configurableClassInstantiation()
    {
    }

    /**
     *
     * @param joinPoint JoinPoint
     */
    @Before("configurableClassInstantiation()")
    public void configurableClassInstantiating(JoinPoint joinPoint)
    {
        InitializerSignature initializerSignature = (InitializerSignature)joinPoint.getSignature();
        Class<?> configurableClass = initializerSignature.getDeclaringType();
        LOGGER.trace("Class configuring {}", configurableClass);

        configuredClassFields.put(configurableClass, new HashMap<>());

        configure(null, configurableClass);
    }

    /**
     *
     * @param joinPoint JoinPoint
     */
    @After("configurableClassInstantiation()")
    public void configurableClassInstantiated(JoinPoint joinPoint)
    {
        InitializerSignature initializerSignature = (InitializerSignature)joinPoint.getSignature();
        Class<?> configurableClass = initializerSignature.getDeclaringType();
        LOGGER.trace("Class configured {}", configurableClass);

        configuredClassFields.get(configurableClass).entrySet().stream().filter(entry ->
            ReflectUtil.get(entry.getKey()) == null
        ).forEach(entry ->
            configureIt(null, entry.getKey(), entry.getValue())
        );

        configuredClassFields.remove(configurableClass);
    }

    /**
     *
     * @param configurable Configurable
     */
    @Pointcut("execution((@com.kissthinker.configure.Configurable *).new(..)) && target(configurable)")
    public void configurableObjectInstantiation(Object configurable)
    {
    }

    /**
     *
     * @param joinPoint JoinPoint
     * @param configurable Configurable
     */
    @Before("configurableObjectInstantiation(configurable)")
    public void configurableObjectInstantiating(JoinPoint joinPoint, Object configurable)
    {
        configuredObjectFields.put(configurable, new HashMap<>());

        LOGGER.trace("Object configuring {}", configurable.getClass());
        configure(configurable, configurable.getClass());
    }

    /**
     *
     * @param joinPoint JoinPoint
     * @param configurable Configurable
     */
    @After("configurableObjectInstantiation(configurable)")
    public void configurableObjectInstantiated(JoinPoint joinPoint, Object configurable)
    {
        LOGGER.trace("Object configured {}", configurable.getClass());

        configuredObjectFields.get(configurable).entrySet().stream().filter(entry ->
            ReflectUtil.get(configurable, entry.getKey()) == null
        ).forEach(entry ->
            configureIt(configurable, entry.getKey(), entry.getValue())
        );

        configuredObjectFields.remove(configurable);
    }

    /**
     *
     * @param proceedingJoinPoint ProceedingJoinPoint
     * @return Object
     * @throws Throwable Issue getting configured
     */
    @Around("get(* *.*) && @annotation(com.kissthinker.configure.Configure)")
    public Object gettingConfigured(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        LOGGER.trace("");

        FieldSignature fieldSignature = (FieldSignature)proceedingJoinPoint.getSignature();
        Field field = fieldSignature.getField();
        Object object = proceedingJoinPoint.getTarget();

        // If class instantiating, put back any null overwrites.
        if (object == null)
        {
            Class<?> class_ = field.getDeclaringClass();

            if (configuredClassFields.containsKey(class_))
            {
                LOGGER.trace("WITHIN class instantiating");

                if (ReflectUtil.get(null, field) == null)
                {
                    ReflectUtil.set(field, configuredClassFields.get(class_).get(field));
                }
            }
        }
        // If object instantiating, put back any null overwrites.
        else if (configuredObjectFields.containsKey(object))
        {
            LOGGER.trace("WITHIN object instantiating");

            if (ReflectUtil.get(object, field) == null)
            {
                ReflectUtil.set(object, field, configuredObjectFields.get(object).get(field));
            }
        }

        return proceedingJoinPoint.proceed();
    }

    /**
     *
     * @param configurable Configurable
     * @param field Field
     * @param configurationClass Configurable class
     * @return True or false
     */
    public boolean configure(Object configurable, Field field, Class<?> configurationClass)
    {
        return configureIt(configurable, field, Factory.create(configurationClass));
    }

    /**
     *
     * @param configurable Configurable
     * @param field Field
     * @param configuration Configuration
     * @return boolean true if given field was configured in configurable with the given configuration
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public boolean configureIt(Object configurable, Field field, Object configuration)
    {
        if (configuration == null)
        {
            return false;
        }

        Class<?> configurationClass = configuration.getClass();
        boolean proxied = false;

        if (configuration instanceof Proxy)
        {
            proxied = true;
            Proxy proxy = (Proxy)configuration;
            configuration = proxy.create(new Proxy.ClassInfo(field.getType()).parentClass(field.getDeclaringClass()).parent(configurable));
        }

        boolean configured = ReflectUtil.set(configurable, field, configuration);

        if (configured)
        {
            if (LOGGER.isTraceEnabled())
            {
                if (proxied)
                {
                    LOGGER.trace("Configured field '{}' with {} via proxy type {}", va(field.getName(), configuration, configurationClass));
                }
                else
                {
                    LOGGER.trace("Configured field '{}' with {}", field.getName(), configuration);
                }
            }

            if (configurable == null)
            {
                Class<?> class_ = field.getDeclaringClass();

                if (configuredClassFields.containsKey(class_))
                {
                    configuredClassFields.get(class_).put(field, configuration);
                }
            }
            else if (configuredObjectFields.containsKey(configurable))
            {
                configuredObjectFields.get(configurable).put(field, configuration);
            }
        }
        else
        {
            LOGGER.trace("Failed to configure field '{}' with {}", field.getName(), configuration);
        }

        return configured;
    }

    /**
     *
     * @param configurable Configurable
     * @param configurableClass Configurable class
     */
    private void configure(Object configurable, Class<?> configurableClass)
    {
        if (configurableClass != null)
        {
            for (Field field : configurableClass.getDeclaredFields())
            {
                if (configuredClassFields.containsKey(field.getDeclaringClass()) && !Modifier.isStatic(field.getModifiers()))
                {
                    continue;
                }

                if (field.isAnnotationPresent(Configure.class))
                {
                    try
                    {
                        field.setAccessible(true);

                        if (!configure(configurable, configurableClass, field))
                        {
                            LOGGER.warn("Did not configure field '{}' on '{}' in environment {} => check environment and available configurations",
                                         va(field.getName(), configurableClass, Environment.RUNTIME));
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            configure(configurable, configurableClass.getSuperclass());
        }
    }

    /**
     *
     * @param configurable configurable
     * @param configurableClass Configurable class
     * @param field Field
     * @return true if field for given configurable was actually configured
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    private boolean configure(Object configurable, Class<?> configurableClass, Field field) throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        LOGGER.trace("Field '{}' needs configuring", field.getName());
        return configurer.configure(configurable, configurableClass, field);

        /*
        TODO Get rid of this original code.
        Configurer configurer = null;

        String applicationConfigurer = System.getProperty("application.configurer");

        if (applicationConfigurer != null && !"".equals(applicationConfigurer))
        {
            @SuppressWarnings("unchecked")
            Class<Configurer> configurerClass = (Class<Configurer>)Class.forName(applicationConfigurer);
            Constructor<Configurer> configurerConstructor = configurerClass.getConstructor(Configurator.class);

            Configurer configurer configurerConstructor.newInstance(this);


        }
        else
        {
            // Apply the defaults with the following order.
            configurer = new IDConfigurer(this);

            configurer.next(new ApplicationPropertiesConfigurer(this))
                      .next(new ConfigurationsConfigurer(this))
                      .next(new FieldTypeConfigurer(this))
                      // .next(new JavaXMLConfigurer(this))
                      // .next(new IoCConfigurer(this))
                      .next(new OtherwiseConfigurer(this));
        }

        return configurer.configure(configurable, field);*/
    }
}