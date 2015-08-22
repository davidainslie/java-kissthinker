package com.kissthinker.concurrency;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kissthinker.configure.Configurable;
import com.kissthinker.configure.Configure;
import com.kissthinker.object.ClassUtil;
import com.kissthinker.object.Singleton;
import com.kissthinker.reflect.ReflectUtil;
import com.kissthinker.text.StringUtil;
import static java.text.MessageFormat.format;

/**
 * A general purpose concurrency utility.
 * <p>
 * Concurrency problems are usually categorised as either "high blocking" e.g involve a lot of IO or "low blocking" e.g involve a lot of processing.<p>
 * In either case, a thread pool should be tuned as to the size and whether the size is fixed or dynamic.<br/.
 * TODO Allow this general purpose utility be instantiated more than once and so can be configured to the type of problem at hand.
 *
 * @author David Ainslie
 *
 */
@Singleton
@Configurable
public final class ExecutorUtil
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorUtil.class);

    /** */
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;

    /** TODO Probably going to remove this one. */
    private static final ScheduledThreadPoolExecutor SCHECULED_THREAD_POOL_EXECUTOR;

    /** Name to use for core pool, where each thread will have this name with a suffix of thread number. */
    @Configure(id="thread.core.pool.name", otherwise="ExecutorUtil-ThreadPool")
    private static final String CORE_POOL_NAME = null;

    /** Used only when setting the core pool size dynamically which is done based on the number of processors. */
    @Configure(id="thread.core.pool.size.factor", otherwise="20")
    private static final Integer CORE_POOL_SIZE_FACTOR = null;

    /** */
    @Configure(id="thread.core.pool.size")
    private static final Integer CORE_POOL_SIZE = null;

    /** */
    @Configure(id="thread.maximum.core.pool.size")
    private static final Integer MAXIMUM_CORE_POOL_SIZE = null;

    /** */
    @Configure(id="thread.core.keep.alive.minutes", otherwise="1")
    private static final Integer KEEP_ALIVE_CORE_MINUTES = null;

    /** */
    @Configure(id="thread.scheduled.pool.size", otherwise="20")
    private static final Integer SCHEDULED_POOL_SIZE = null;

    /** */
    private static boolean shutdown;

    /**
     * Class initialisation.
     */
    static
    {
        // Thread pools.
        int availableProcessors = Runtime.getRuntime().availableProcessors();

        if (CORE_POOL_SIZE == null)
        {
            try
            {
                LOGGER.info("Configuring core thread pool dynamically...");

                ReflectUtil.set(ExecutorUtil.class.getDeclaredField("MAXIMUM_CORE_POOL_SIZE"), availableProcessors * CORE_POOL_SIZE_FACTOR);

                int corePoolSize = MAXIMUM_CORE_POOL_SIZE / 2;
                ReflectUtil.set(ExecutorUtil.class.getDeclaredField("CORE_POOL_SIZE"), corePoolSize);
            }
            catch (Exception e)
            {
                throw new Error(e);
            }
        }

        // TODO Unit test - first method execute(Runnable) shows (initial) pool size of zero
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_CORE_POOL_SIZE,
                                                      KEEP_ALIVE_CORE_MINUTES, TimeUnit.MINUTES,
                                                      new ArrayBlockingQueue<Runnable>(MAXIMUM_CORE_POOL_SIZE, true),
                                                      new CoreThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

        LOGGER.info("For {} CPUs, configured thread pool executor: {}", availableProcessors, StringUtil.toString(THREAD_POOL_EXECUTOR));

        SCHECULED_THREAD_POOL_EXECUTOR = new ScheduledThreadPoolExecutor(SCHEDULED_POOL_SIZE, new ScheduledThreadFactory());
        LOGGER.info("Configured scheduled thread pool executor: {}", StringUtil.toString(SCHECULED_THREAD_POOL_EXECUTOR));

        // Shutdown hook.
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            /**
             *
             * @see java.lang.Thread#run()
             */
            @Override
            public void run()
            {
                LOGGER.warn("Running {} Shutdown Hook", ExecutorUtil.class);
                shutdown();
            }
        });
    }

    /**
     * @param runnable
     */
    public static void execute(Runnable runnable)
    {
        LOGGER.trace("Thread pool size = {}", THREAD_POOL_EXECUTOR.getPoolSize());
        THREAD_POOL_EXECUTOR.execute(runnable);
    }

    /**
     *
     * @param runnable
     * @return
     */
    public static Future<?> submit(Runnable runnable)
    {
        LOGGER.trace("Thread pool size = {}", THREAD_POOL_EXECUTOR.getPoolSize());
        return THREAD_POOL_EXECUTOR.submit(runnable);
    }

    /**
     *
     * @param <T>
     * @param callable
     * @return
     */
    public static <T> Future<T> submit(Callable<T> callable)
    {
        LOGGER.trace("Thread pool size = {}", THREAD_POOL_EXECUTOR.getPoolSize());
        return THREAD_POOL_EXECUTOR.submit(callable);
    }

    /**
     *
     * @param delay
     * @param timeUnit
     * @param runnable
     * @return
     */
    public static ScheduledFuture<?> schedule(long delay, TimeUnit timeUnit, Runnable runnable)
    {
        return SCHECULED_THREAD_POOL_EXECUTOR.schedule(runnable, delay, timeUnit);
    }

    /**
     *
     * @param initialDelay
     * @param period
     * @param timeUnit
     * @param runnable
     * @return
     */
    public static ScheduledFuture<?> scheduleAtFixedRate(long initialDelay, long period, TimeUnit timeUnit, Runnable runnable)
    {
        return SCHECULED_THREAD_POOL_EXECUTOR.scheduleAtFixedRate(runnable, initialDelay, period, timeUnit);
    }

    /**
     *
     * @param period
     * @param timeUnit
     * @param runnable
     * @return
     */
    public static ScheduledFuture<?> scheduleAtFixedRate(long period, TimeUnit timeUnit, Runnable runnable)
    {
        return SCHECULED_THREAD_POOL_EXECUTOR.scheduleAtFixedRate(runnable, 0, period, timeUnit);
    }

    /**
     *
     */
    public static void shutdown()
    {
        LOGGER.warn("Controlled shutdown...");
        shutdown = true;
        THREAD_POOL_EXECUTOR.shutdown();
        SCHECULED_THREAD_POOL_EXECUTOR.shutdown();

        try
        {
            LOGGER.warn("Sanity shutdown checks for 'thread pool executor'");

            // Wait a while for existing tasks to terminate
            if (!THREAD_POOL_EXECUTOR.awaitTermination(60, TimeUnit.SECONDS))
            {
                LOGGER.warn("Forcing shutdown of 'thread pool executor'");
                THREAD_POOL_EXECUTOR.shutdownNow(); // Cancel currently executing tasks

                // Wait a while for tasks to respond to being cancelled
                if (!THREAD_POOL_EXECUTOR.awaitTermination(60, TimeUnit.SECONDS))
                {
                    LOGGER.warn("THREAD_POOL_EXECUTOR did not terminate correctly");
                }
            }
        }
        catch (InterruptedException e)
        {
            // (Re-)Cancel if current thread also interrupted
            THREAD_POOL_EXECUTOR.shutdownNow();

            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }

        try
        {
            LOGGER.warn("Sanity shutdown checks for 'scheduled thread pool executor'");

            // Wait a while for existing tasks to terminate
            if (!SCHECULED_THREAD_POOL_EXECUTOR.awaitTermination(60, TimeUnit.SECONDS))
            {
                LOGGER.warn("Forcing shutdown of 'scheduled thread pool executor'");
                SCHECULED_THREAD_POOL_EXECUTOR.shutdownNow(); // Cancel currently executing tasks

                // Wait a while for tasks to respond to being cancelled
                if (!SCHECULED_THREAD_POOL_EXECUTOR.awaitTermination(60, TimeUnit.SECONDS))
                {
                    LOGGER.warn("SCHECULED_THREAD_POOL_EXECUTOR did not terminate correctly");
                }
            }
        }
        catch (InterruptedException e)
        {
            // (Re-)Cancel if current thread also interrupted
            SCHECULED_THREAD_POOL_EXECUTOR.shutdownNow();

            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    /** */
    public static boolean isShutdown()
    {
        return shutdown;
    }

    /** */
    public static int coreCurrentPoolSize()
    {
        return THREAD_POOL_EXECUTOR.getPoolSize();
    }

    /** */
    public static int corePoolSize()
    {
        return THREAD_POOL_EXECUTOR.getCorePoolSize();
    }

    /** */
    public static int coreMaximumPoolSize()
    {
        return THREAD_POOL_EXECUTOR.getMaximumPoolSize();
    }

    /**
     *
     * @return
     */
    public static int awaitingTaskCount()
    {
        return THREAD_POOL_EXECUTOR.getQueue().size();
    }

    /**
     * Utility.
     */
    private ExecutorUtil()
    {
        super();
    }

    /**
     *
     * @author David Ainslie
     */
    static class CoreThreadFactory implements ThreadFactory
    {
        /**
         *
         * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
         */
        @Override
        public Thread newThread(Runnable runnable)
        {
            Thread thread =  new Thread(runnable);

            synchronized (CoreThreadFactory.class)
            {
                thread.setName(format("{0}-{1}", ExecutorUtil.CORE_POOL_NAME, THREAD_POOL_EXECUTOR.getPoolSize()));
            }

            return thread;
        }
    }

    /**
     *
     * @author David Ainslie
     */
    static class ScheduledThreadFactory implements ThreadFactory
    {
        /** */
        private static final AtomicInteger count = new AtomicInteger(0);

        /**
         *
         * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
         */
        @Override
        public Thread newThread(Runnable runnable)
        {
            Thread thread =  new Thread(runnable);
            thread.setName(String.format("%s-ScheduledThreadPool-%d", ClassUtil.name(ExecutorUtil.class), count.incrementAndGet()));

            return thread;
        }
    }
}