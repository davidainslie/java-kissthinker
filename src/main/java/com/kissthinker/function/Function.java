package com.kissthinker.function;

import java.util.Collection;

/**
 * This class was written for Java 7 and should not be used for Java 8+.
 * Function to wrap up (delegate to) a method on a proxied object.
 *
 * Using this paradigm, a method on an object is not immediately called (in client code), but the call is set up and stored for later use.
 * This interface can be implemented directly. However, it should be created via {@link Fn#function(Object)} e.g.
 * <pre>
 * {@code
 * import static com.kissthinker.function.Fn.from;
 * import static com.kissthinker.function.Fn.function;
 *
 * Function function = function(from(AFunction.class).doSomething());
 * }
 * </pre>
 * By creating a Function via {@link Fn}, the generated function is actually a proxy that allows it to work with collections via functional programming.
 * For example, a (proxied) function can use the FunctionUtil methods {@link Fn#filter(Collection, Function)}, {@link Fn#map(Collection, Function)}, TODO reduce()
 * A function should (though it is optional) take at least one parameter, and should return a (function outcome) result.
 * As the return is not optional, a declaration of a function could still return a Void to get around this is necessary.
 * Also, the actual method being wrapped by a function should be public. Again this makes sense in terms of a function that is passes around and so needs to be accessible.
 * Another use of this function paradigm, is to avoid using the Visitor pattern.
 * Visitor is intrusive on your API and not flexible when new concrete Visitors and Visitables are added.
 * For an example of using functions to avoid the Visitor pattern, take a look at FnTest.applyFunctionWithPolymorphismNotDoubleDispatchAsWithVisitor()
 * @see com.kissthinker.function.Fn
 * @author David Ainslie
 *
 * @param <R> Return type
 */
public interface Function<R>
{
    /**
     *
     * @param args Arguments to function
     * @return R the result of applying a delegated method.
     */
    R apply(Object... args);

    /**
     * A convenience callback to be implemented and shall be executed when {@link Function#apply(Object...)} is called.
     *
     * When constructing a "function", instead of the usual call:
     * Function function = function(from(AFunction.class).doSomething()); OR
     * Function function = function(from(this).doSomething());
     * can instead implement this interface and call thus:
     * Function function = function(from(new Function.Callback implement));
     * Notice that you do not need the final call to the method, in this case "onCallback" (instead of "doSomething")
     * @author David Ainslie
     *
     * @param <R> Return type
     * @param <O> Object type
     */
    interface Callback<R, O>
    {
        /**
         *
         * @param object Object
         * @return R the result
         */
        R onCallback(O object);
    }
}