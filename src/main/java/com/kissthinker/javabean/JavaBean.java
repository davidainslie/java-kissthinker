package com.kissthinker.javabean;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.FieldSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.kissthinker.collection.array.ArrayUtil.va;

/**
 * Marker interface to highlight a "class" as a JavaBean i.e. a class where any fields marked up with @Property fire property change events to property listeners.
 * <br/>
 * To "listen" to a {@link JavaBean} go through the 3rd party (support/helper) class {@link JavaBeanSupport}
 * @author David Ainslie
 *
 */
public interface JavaBean
{
    /**
     * Mark up a class to be a {@link JavaBean} with this interface, if properties are to be given to {@link JavaBeanSupport} as enum.
     * <br/>
     * TODO Currently a declared enum can only match "exactly" the name of the variable of a "property" e.g.
     * <pre>
     *  // If you have the following declared in a marked up JavaBean
     *  {@code @Property} private String id = "Scooby";
     *  
     *  // An associated enum would have to be declared as "id" and not say "ID" as shown in the following (where the chosen name Properties is irrelevant)
     *  public enum Properties {id}
     * </pre>
     * @author David Ainslie
     *
     */
    public interface Enumerated<P extends Enum<P>> extends JavaBean
    {        
    }

    /**
     * 
     * @author David Ainslie
     *
     */
    @Aspect
    public static class JavaBeanInterceptor
    {
        /**
         * @param proceedingJoinPoint
         * @param target
         * @param newValue
         * @return
         * @throws Throwable
         */
        @Around("set(* *.*) && @annotation(com.kissthinker.javabean.Property) && target(javaBean) && args(newValue)")
        public Object setProperty(ProceedingJoinPoint proceedingJoinPoint, JavaBean javaBean, Object newValue) throws Throwable
        {
            FieldSignature fieldSignature = (FieldSignature)proceedingJoinPoint.getSignature();
            Field field = fieldSignature.getField();
            field.setAccessible(true);
            Object oldValue = field.get(javaBean);
            
            Object result = proceedingJoinPoint.proceed();
            
            JavaBeanSupport.firePropertyChange(javaBean, field.getName(), oldValue, newValue);
            
            return result;
        }
    }

    /*public static <C extends Collection<?>> C javaBeanCollection(C collection)
    {
    }*/
    
    /**
     * 
     * @author David Ainslie
     *
     */
    @Aspect
    public static class CollectionInterceptor
    {
        /**
         * 
         * @param proceedingJoinPoint
         * @param collection Note the use of @SuppressWarnings("rawtypes") as AspectJ cannot handle a declaration of Collection<?>, the compile error would be<br/>
         * parameterized types not supported for this and target pointcuts (erasure limitation)
         * @param object
         * @return
         * @throws Throwable
         */
        @Around("call(* java.util.Collection+.add(..)) && target(collection) && args(object)")
        public Object add(ProceedingJoinPoint proceedingJoinPoint, @SuppressWarnings("rawtypes") Collection collection, final Object object) throws Throwable
        {
            Object result = proceedingJoinPoint.proceed();
            JavaBeanSupport.callback("onAdd", collection, object);            
            return result;
        }

        /**
         * 
         * @param proceedingJoinPoint
         * @param collection
         * @param object
         * @return
         * @throws Throwable
         */
        @Around("call(* java.util.Collection+.remove(..)) && target(collection) && args(object)")
        public Object remove(ProceedingJoinPoint proceedingJoinPoint, @SuppressWarnings("rawtypes") Collection collection, final Object object) throws Throwable
        {
            Object result = proceedingJoinPoint.proceed();
            JavaBeanSupport.callback("onRemove", collection, object);            
            return result;
        }
    }

    /**
     * TODO Regarding this aspect in conjuction with MapListener, code was copied/pasted/ameneded from the equivalent above i.e. needs sorting out.
     * @author David Ainslie
     *
     */
    @Aspect
    public static class MapInterceptor
    {
        /** */
        private static final Logger LOGGER = LoggerFactory.getLogger(MapInterceptor.class);
        
        /**
         * 
         * @param proceedingJoinPoint
         * @param map Note the use of @SuppressWarnings("rawtypes") as AspectJ cannot handle a declaration of Map<?, ?>, the compile error would be<br/>
         * parameterized types not supported for this and target pointcuts (erasure limitation)
         * @param key
         * @param value
         * @return
         * @throws Throwable
         */
        @Around("call(* java.util.Map+.put(..)) && target(map) && args(key, value)")
        public Object put(ProceedingJoinPoint proceedingJoinPoint, @SuppressWarnings("rawtypes") Map map, final Object key, final Object value) throws Throwable
        {
            LOGGER.trace("Intercepted method 'put' on map {} for given key/value {}/{}", va(map, key, value));
            
            Object result = proceedingJoinPoint.proceed();
            JavaBeanSupport.callback("onPut", map, key, value);
            return result;
        }

        /**
         * 
         * @param proceedingJoinPoint
         * @param map
         * @param key
         * @return
         * @throws Throwable
         */
        @Around("call(* java.util.Map+.remove(..)) && target(map) && args(key)")
        public Object remove(ProceedingJoinPoint proceedingJoinPoint, @SuppressWarnings("rawtypes") Map map, final Object key) throws Throwable
        {
            LOGGER.trace("Intercepted method 'remove' on map {} for given key {}", map, key);
            
            Object result = proceedingJoinPoint.proceed();
            // Where result is the value that was removed (if indeed a key was found for a value to be removed).
            
            if (result != null)
            {
                JavaBeanSupport.callback("onRemove", map, key, result);
            }
            
            return result;
        }
    }    
}