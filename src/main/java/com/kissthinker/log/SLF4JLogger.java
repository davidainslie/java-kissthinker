package com.kissthinker.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kissthinker.configure.Configuration;
import com.kissthinker.proxy.Proxy;
import static com.kissthinker.system.Environment.ANY;

/**
 * @author David Ainslie
 *
 */
@Configuration(environment = ANY, proxy = SLF4JLogger.SLF4JLoggerProxy.class)
public interface SLF4JLogger extends Logger
{
    /**
     *
     * @author David Ainslie
     *
     */
    class SLF4JLoggerProxy implements Proxy
    {
        /**
         *
         * @see com.kissthinker.proxy.Proxy#create(com.kissthinker.proxy.Proxy.ClassInfo)
         */
        @SuppressWarnings("unchecked")
        @Override
        public <O, P> O create(Proxy.ClassInfo<O, P> classInfo)
        {
            Logger logger = LoggerFactory.getLogger(classInfo.parentClass());
            return (O)logger;
        }
    }
}