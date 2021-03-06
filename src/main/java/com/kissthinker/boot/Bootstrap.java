package com.kissthinker.boot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kissthinker.concurrency.ExecutorUtil;
import com.kissthinker.function.Function;
import com.kissthinker.object.Type;
import com.kissthinker.reflect.ReflectUtil;
import com.kissthinker.service.Service;
import com.kissthinker.service.Services;
import com.kissthinker.system.Environment;
import static com.kissthinker.function.Fn.from;
import static com.kissthinker.function.Fn.function;

/**
 * Bootstrap classes such as those with a "main" method or unit tests may be "marked up" by this annotation to allow for "automatic initialisation".
 *
 * Automatic initialisation includes starting all services that have been marked up with {@link Service}
 * There is an inner static class that handles the bootstrapping automatically as an "aspect".
 * NOTE That "bootstapping" can still be called directly (and so not have to annotate with @Bootstrap).
 * In this case, client code would call the following:
 * Note that calling the following multiple times would still only initialise once (this is handled by the implementation).
 * {@code
 * Bootstrap.Bootstrapper.bootstrap(Environment);
 * }
 *
 * Why choose this way? Maybe some "pre configuration" is first required e.g in a test setting the application's "environement" within the code.
 * Don't understand why the inner class {@link Bootstrapper#bootstrap(JoinPoint, Bootstrap)} does not call {@link Bootstrapper#prepareShutdown()} directly?
 * First of all, this is for demonstation purposes, and second, as subclasses of {@link Bootstrapper} can pass in functions to {@link Bootstrapper#bootstrap(Environment, Function...)}
 * to be executed, we may as well keep with consistency is the "parent" class and also pass in functions.
 * @author David Ainslie
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Bootstrap
{
    /**
     *
     * @return Environment
     */
    Environment environment() default Environment.INTEGRATION;

    /**
     * Bootstrap through this class/aspect.
     * @author David Ainslie
     *
     */
    @Aspect
    static class Bootstrapper
    {
        /** */
        private static final Logger LOGGER = LoggerFactory.getLogger(Bootstrapper.class);

        /** */
        private static final AtomicBoolean BOOTSTRAPPED = new AtomicBoolean(false);

        /**
         *
         * @param environment Environment to boostrap within
         * @param functions provide functions (or none) to be executed upon this initial bootstrap.
         */
        public static void bootstrap(Environment environment, Function<?>... functions)
        {
            if (BOOTSTRAPPED.compareAndSet(false, true))
            {
                logSystemProperties();
                LOGGER.info("Bootstrapping...");

                if (System.getProperty("environment") == null)
                {
                    try
                    {
                        System.setProperty("environment", environment.name());
                        ReflectUtil.set(Environment.class.getDeclaredField("RUNTIME"), environment);
                        LOGGER.info("(Re)Set runtime environment to {}", Environment.RUNTIME);
                    }
                    catch (Exception e)
                    {
                        throw new Error(e);
                    }
                }

                if (functions != null)
                {
                    for (Function<?> function : functions)
                    {
                        LOGGER.info("Executing function: {}", function);
                        function.apply();
                    }
                }
            }
        }

        /**
         *
         * @param joinPoint Defined point in execution of application
         * @param bootstrap Bootstrap
         */
        @Before("within(@com.kissthinker.boot.Bootstrap *) && @annotation(bootstrap)")
        public void bootstrap(JoinPoint joinPoint, Bootstrap bootstrap)
        {
            bootstrap(bootstrap.environment(), function(from(this).setupEnvironment()),
                                               function(from(this).prepareShutdown()));
        }

        /**
         * Setup the environment such as starting all necessary "services".
         * @return Void to allow this method to be wrapped up in a {@link Function}
         */
        public Void setupEnvironment()
        {
            LOGGER.info("");
            Services.start();
            return Type.VOID;
        }

        /**
         *
         * @return Void to allow this method to be wrapped up in a {@link Function}
         */
        public Void prepareShutdown()
        {
            Runtime.getRuntime().addShutdownHook(new Thread()
            {
                @Override
                public void run()
                {
                    LOGGER.info("");
                    ExecutorUtil.shutdown();
                }
            });

            return Type.VOID;
        }

        /** */
        private static void logSystemProperties()
        {
            LOGGER.info("");
            LOGGER.info("-----------------");
            LOGGER.info("System Properties");
            LOGGER.info("-----------------");
            
            Properties properties = System.getProperties();
            
            for (Entry<Object, Object> entry : properties.entrySet())
            {
                LOGGER.info("{} = {}", entry.getKey(), entry.getValue());
            }
            
            LOGGER.info("");
            LOGGER.info("");
        }
    }
}