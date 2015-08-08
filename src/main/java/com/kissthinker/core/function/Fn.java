package com.kissthinker.core.function;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kissthinker.core.collection.CollectionUtil;
import com.kissthinker.core.factory.Factory;
import static com.kissthinker.core.collection.CollectionUtil.newInstance;

/**
 * This class was written for Java 7 and should not be used for Java 8+.
 * Utility to invoke methods (on some class/object) not directly, but as a function - a function that can be passed around and have its {@link Function#apply(Object...)} called.
 * <br/>
 * Note that originally, this class was name FunctionUtil, as Fn is not descriptive. However, Fn was eventually chosen because of generics e.g<br/>
 * Because of generics, we would have to code:<br/>
 * fxQuotePublisher.subscribe(new FX(Currency.EUR, Currency.USD), function(from(this).onQuote(null)), FunctionUtil.<Boolean>function(from(this).invalidCriteria(null)));<br/>
 * instead of
 * fxQuotePublisher.subscribe(new FX(Currency.EUR, Currency.USD), function(from(this).onQuote(null)), function(from(this).invalidCriteria(null)));<br/>
 * but now with this class named as Fn, we can do:<br/>
 * fxQuotePublisher.subscribe(new FX(Currency.EUR, Currency.USD), function(from(this).onQuote(null)), Fn.<Boolean>function(from(this).invalidCriteria(null)));<br/>
 * which is still not great, but if that fabulous library op4j names its functions classes as FnXXX (such as FnString), then some others must think this is okay.<br/>
 * Note that this utility also provides the standard functional programming of functions on collections through filter, map and reduce.
 * TODO This Implementation does not cater for invoking static methods as functions.<br/>
 * TODO MapReduce.<br/>
 * This is because, the implementation used in this class uses CBLIB, and static method interception is not handled.<br/>
 * A solution to this, would be to implement a new utility that used AspectJ.
 * See {@link FnTest} for examples on how to use this utility.
 *
 * @author David Ainslie
 *
 */
public final class Fn
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(Fn.class);

    /** The function that will be created (on current thread). */
    private static final ThreadLocal<Function<?>> FUNCTION = new ThreadLocal<>();

    /** */
    private static final ThreadLocal<Boolean> INITIALISING_FUNCTION = new ThreadLocal<Boolean>()
    {
        /**
         * @see java.lang.ThreadLocal#initialValue()
         */
        @Override
        protected Boolean initialValue()
        {
            return true;
        }
    };

    /**
     * Get a "function" object which essentially wraps an object given by method {@link #from(Object)} or object generated from given class given by method {@link #from(Class)}
     * @param <R>
     * @param dummy is ignored, as the Function has already been created and stored on the current thread.
     * It is essentially a dummy value (which will in fact be null) for the result of the delegated method execution.
     * @return Function<R> that has already been created and stored on the current thread.
     */
    public static <R> Function<R> function(Object dummy)
    {
        @SuppressWarnings("unchecked")
        Function<R> function = (Function<R>)FUNCTION.get();

        return function;
    }

    /**
     * To create a "function", an object (instantiated from the given class) is wrapped up as a "function" by proxy.
     * @param <O>
     * @param class_
     * @return O a proxied object that can be executed as a function
     */
    public static <O> O from(Class<O> class_)
    {
        @SuppressWarnings({"unchecked", "rawtypes"})
        O proxy = (O)Enhancer.create(class_, new Class[]{Function.class}, new FunctionInterceptor(class_));

        INITIALISING_FUNCTION.set(false);

        return proxy;
    }

    /**
     * To create a "function", an object is wrapped up as a "function" by proxy.<br/>
     * @param <O>
     * @param object
     * @return O a proxied object that can be executed as a function
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <O> O from(O object)
    {
        if (object instanceof Function.Callback)
        {
            Function.Callback proxy = (Function.Callback)Enhancer.create(Function.Callback.class, new Class[]{Function.class, Function.Callback.class}, new FunctionInterceptor(object));
            INITIALISING_FUNCTION.set(false);
            proxy.onCallback(new Object());

            return (O)proxy;
        }
        else
        {
            O proxy = (O)Enhancer.create(object.getClass(), new Class[]{Function.class}, new FunctionInterceptor(object));

            INITIALISING_FUNCTION.set(false);

            return proxy;
        }
    }

    /**
     * Convenience method to execute given function on given target, instead of the (original) proxied target.<br/>
     * Primarily used by {@link CollectionUtil}
     * @param <R>
     * @param function
     * @param target
     * @param args
     * @return R result of executing given function
     */
    public static <R> R apply(Function<R> function, Object target, Object... args)
    {
        FunctionInterceptor<R> functionInterceptor = (FunctionInterceptor<R>)function;
        return functionInterceptor.applyOn(target, args);
    }

    /**
     *
     * @param <O>
     * @param collection
     * @param functions
     * @return
     */
    public static <O> Collection<O> forEach(Collection<O> collection, List<Function<O>> functions)
    {
        for (Function<O> function : functions)
        {
            collection = forEach(collection, function);
        }

        return collection;
    }

    /**
     *
     * @param <O>
     * @param collection
     * @param function
     * @return
     */
    public static <O> Collection<O> forEach(Collection<O> collection, Function<O> function)
    {
        Collection<O> forEachCollection = newInstance(collection);

        for (O object : collection)
        {
            forEachCollection.add(function.apply(object));
        }

        return forEachCollection;
    }

    /**
     *
     * @param <O>
     * @param list
     * @param functions
     * @return
     */
    public static <O> List<O> forEach(List<O> list, List<Function<O>> functions)
    {
        return (List<O>)forEach((Collection<O>)list, functions);
    }

    /**
     *
     * @param <O>
     * @param list
     * @param function
     * @return
     */
    public static <O> List<O> forEach(List<O> list, Function<O> function)
    {
        return (List<O>)forEach((Collection<O>)list, function);
    }

    /**
     *
     * @param <O>
     * @param set
     * @param functions
     * @return
     */
    public static <O> Set<O> forEach(Set<O> set, List<Function<O>> functions)
    {
        return (Set<O>)forEach((Collection<O>)set, functions);
    }

    /**
     *
     * @param <O>
     * @param set
     * @param function
     * @return
     */
    public static <O> Set<O> forEach(Set<O> set, Function<O> function)
    {
        return (Set<O>)forEach((Collection<O>)set, function);
    }

    /**
     *
     * @param <O>
     * @param collection
     * @param functions
     * @return Collection<O>
     */
    public static <O> Collection<O> filter(Collection<O> collection, List<Function<Boolean>> functions)
    {
        for (Function<Boolean> function : functions)
        {
            collection = filter(collection, function);
        }

        return collection;
    }

    /**
     *
     * @param <O>
     * @param collection
     * @param function
     * @return Collection<O>
     */
    public static <O> Collection<O> filter(Collection<O> collection, Function<Boolean> function)
    {
        Collection<O> filteredCollection = newInstance(collection);

        // The following in Java 8 could be replaced with:
        // filteredCollection.addAll(collection.stream().filter(function::apply).collect(Collectors.toList()));
        // But then again, this whole class is not needed when using Java 8.
        for (O object : collection)
        {
            if (function.apply(object))
            {
                filteredCollection.add(object);
            }
        }

        return filteredCollection;
    }

    /**
     *
     * @param <O>
     * @param list
     * @param functions
     * @return List<O>
     */
    public static <O> List<O> filter(List<O> list, List<Function<Boolean>> functions)
    {
        return (List<O>)filter((Collection<O>)list, functions);
    }

    /**
     *
     * @param <O>
     * @param list
     * @param function
     * @return List<O>
     */
    public static <O> List<O> filter(List<O> list, Function<Boolean> function)
    {
        return (List<O>)filter((Collection<O>)list, function);
    }

    /**
     *
     * @param <O>
     * @param set
     * @param functions
     * @return Set<O>
     */
    public static <O> Set<O> filter(Set<O> set, List<Function<Boolean>> functions)
    {
        return (Set<O>)filter((Collection<O>)set, functions);
    }

    /**
     *
     * @param <O>
     * @param set
     * @param function
     * @return Set<O>
     */
    public static <O> Set<O> filter(Set<O> set, Function<Boolean> function)
    {
        return (Set<O>)filter((Collection<O>)set, function);
    }

    /**
     *
     * @param <I>
     * @param <O>
     * @param collection
     * @param function
     * @return Collection<O>
     */
    public static <I, O> Collection<O> map(Collection<I> collection, Function<O> function)
    {
        @SuppressWarnings("unchecked")
        Collection<O> mappedCollection = (Collection<O>)newInstance(collection);

        for (I input : collection)
        {
            mappedCollection.add(apply(function, input));
        }

        return mappedCollection;
    }

    /**
     *
     * @param <I>
     * @param <O>
     * @param list
     * @param function
     * @return List<O>
     */
    public static <I, O> List<O> map(List<I> list, Function<O> function)
    {
        return (List<O>)map((Collection<I>)list, function);
    }

    /**
     *
     * @param <I>
     * @param <O>
     * @param set
     * @param function
     * @return Set<O>
     */
    public static <I, O> Set<O> map(Set<I> set, Function<O> function)
    {
        return (Set<O>)map((Collection<I>)set, function);
    }

    /**
     * @param <I>
     * @param <O>
     * @param collection
     * @param seed
     * @param function
     * @return O
     */
    public static <I, O> O foldLeft(Collection<I> collection, O seed, Function<O> function)
    {
        O accumulation = seed;

        for (I input : collection)
        {
            accumulation = function.apply(input, accumulation);
        }

        return accumulation;
    }

    /**
     * @param <I>
     * @param <O>
     * @param list
     * @param seed
     * @param function
     * @return O
     */
    public static <I, O> O foldLeft(List<I> list, O seed, Function<O> function)
    {
        return foldLeft((Collection<I>)list, seed, function);
    }

    /**
     * @param <I>
     * @param <O>
     * @param set
     * @param seed
     * @param function
     * @return O
     */
    public static <I, O> O foldLeft(Set<I> set, O seed, Function<O> function)
    {
        return foldLeft((Collection<I>)set, seed, function);
    }

    // TODO foldRight
    
    // TODO reduceLeft

    // TODO reduceRight

    /**
     * Utility
     */
    private Fn()
    {
        super();
    }

    /**
     *
     * @author David Ainslie
     *
     * @param <R>
     */
    private static class FunctionInterceptor<R> implements MethodInterceptor, Function<R>
    {
        /** */
        private final Class<?> objectClass;

        /** */
        private final Object object;

        /** */
        private Method delegateMethod;

        /**
         *
         * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], net.sf.cglib.proxy.MethodProxy)
         */
        @Override
        public Object intercept(Object object, Method method, Object[] methodArgs, MethodProxy methodProxy) throws Throwable
        {
            if (method.getName().equals("apply"))
            {
                return apply(methodArgs);
            }
            else if (!INITIALISING_FUNCTION.get())
            {
                // TODO The following has become a tad messy, and some of it may not actually be necessary - but it's working so I'll come back to this.
                try
                {
                    Class<?>[] methodArgsClasses = new Class<?>[methodArgs.length];

                    for (int i = 0; i < methodArgs.length; i++)
                    {
                        methodArgsClasses[i] = methodArgs[i].getClass();
                    }

                    // TODO Get my original MethodUtil that does "pattern matching" of method arguements.
                    delegateMethod = MethodUtils.getAccessibleMethod(objectClass, method.getName(), methodArgsClasses);
                }
                catch (Exception e)
                {
                    // Ignore
                }

                if (delegateMethod == null)
                {
                    delegateMethod = match(method, objectClass);
                }

                if (delegateMethod == null)
                {
                    throw new NoSuchMethodException(method.toString());
                }

                delegateMethod.setAccessible(true);
                FUNCTION.set(this);

                return null;
            }
            else
            {
                return methodProxy.invokeSuper(object, methodArgs);
            }
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
            return "" + delegateMethod;
        }

        /**
         *
         * @see com.kissthinker.core.function.Fn.Function#apply(java.lang.Object[])
         */
        @SuppressWarnings("unchecked")
        @Override
        public R apply(Object... args)
        {
            try
            {
                if (delegateMethod.getParameterTypes().length == 0)
                {
                    return (R)delegateMethod.invoke(object);
                }

                return (R)delegateMethod.invoke(object, args);
            }
            catch (Exception e)
            {
                LOGGER.debug("", e);
                throw new RuntimeException(e); // TODO And do I really want to do this? What if you do have the wrong type of args?
            }
        }

        /**
         *
         * @param class_
         */
        private FunctionInterceptor(Class<Object> class_)
        {
            objectClass = class_;
            object = Factory.create(class_);
        }

        /**
         *
         * @param object
         */
        private FunctionInterceptor(Object object)
        {
            objectClass = object.getClass();
            this.object = object;
        }

        /**
         * Convenience method for executing on a different target other than the proxied one.
         * @param target
         * @param args
         * @return
         */
        @SuppressWarnings("unchecked")
        private R applyOn(Object target, Object... args)
        {
            try
            {
                if (delegateMethod.getParameterTypes().length == 0)
                {
                    return (R)delegateMethod.invoke(target);
                }

                return (R)delegateMethod.invoke(target, args);
            }
            catch (Exception e)
            {
                e.printStackTrace(); // TODO Probably remove this line.
                throw new RuntimeException(e);
            }
        }

        /**
         *
         * @param method
         * @param class_
         * @return Method
         */
        private Method match(Method method, Class<?> class_)
        {
            for (Method checkMethod : class_.getDeclaredMethods())
            {
                if (Function.Callback.class.isAssignableFrom(class_) &&
                    "onCallback".equals(checkMethod.getName()) &&
                    checkMethod.getParameterTypes().length == 1)
                {
                    return method;
                }
                else if (checkMethod.equals(method))
                {
                    return method;
                }
            }

            Class<?> superClass = class_.getSuperclass();

            if (superClass != null)
            {
                return match(method, superClass);
            }

            return null;
        }
    }
}