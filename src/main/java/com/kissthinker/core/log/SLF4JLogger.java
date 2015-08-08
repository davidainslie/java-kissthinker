package com.kissthinker.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kissthinker.core.configure.Configuration;
import com.kissthinker.core.proxy.Proxy;
import static com.kissthinker.core.system.Environment.ANY;

/**
 * @author David Ainslie
 *
 */
@Configuration(environment=ANY, proxy=SLF4JLogger.SLF4JLoggerProxy.class)
public interface SLF4JLogger extends Logger
{
    /**
     * 
     * @author David Ainslie
     *
     */
    public static class SLF4JLoggerProxy implements Proxy
    {
        /**
         * 
         * @see com.kissthinker.core.proxy.Proxy#create(com.kissthinker.core.proxy.Proxy.ClassInfo)
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