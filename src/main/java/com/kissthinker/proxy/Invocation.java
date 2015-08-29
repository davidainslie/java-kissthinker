package com.kissthinker.proxy;

import java.util.Arrays;
import com.kissthinker.configure.Configuration;

/**
 * @author David Ainslie
 *
 */
public class Invocation
{
    /** Class maybe annotated with {@link Configuration} TODO Why did I comment this? I know I originally did it when a string ID could be passed. */
    private final Class<?> targetClass;

    /** */
    private final Class<?> returnClass;

    /** */
    private final String methodName;

    /** */
    private final Object[] methodArgs;

    /**
     *
     * @param targetClass Target class
     * @param returnClass Return class
     * @param methodName Method name
     * @param methodArgs Method arguments
     */
    public Invocation(Class<?> targetClass, Class<?> returnClass, String methodName, Object... methodArgs)
    {
        super();
        this.targetClass = targetClass;
        this.returnClass = returnClass == null || void.class.isAssignableFrom(returnClass) ? Void.class : returnClass;
        this.methodName = methodName;
        this.methodArgs = methodArgs;
    }

    /**
     * Getter
     * @return Target class
     */
    public Class<?> targetClass()
    {
        return targetClass;
    }

    /**
     * Getter.
     * @return Return class
     */
    public Class<?> returnClass()
    {
        return returnClass;
    }

    /**
     *
     * @return boolean True or false
     */
    public boolean returns()
    {
        return !void.class.isAssignableFrom(returnClass) && !Void.class.isAssignableFrom(returnClass);
    }

    /**
     * Getter
     * @return Method name
     */
    public String methodName()
    {
        return methodName;
    }

    /**
     * Getter
     * @return Method arguments
     */
    public Object[] methodArgs()
    {
        return methodArgs;
    }

    /**
     * Autogenerated.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((targetClass == null) ? 0 : targetClass.hashCode());
        result = prime * result + Arrays.hashCode(methodArgs);
        result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
        result = prime * result + ((returnClass == null) ? 0 : returnClass.hashCode());
        return result;
    }

    /**
     * Autogenerated.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Invocation other = (Invocation)obj;
        if (targetClass == null)
        {
            if (other.targetClass != null)
                return false;
        }
        else if (!targetClass.equals(other.targetClass))
            return false;
        if (!Arrays.equals(methodArgs, other.methodArgs))
            return false;
        if (methodName == null)
        {
            if (other.methodName != null)
                return false;
        }
        else if (!methodName.equals(other.methodName))
            return false;
        if (returnClass == null)
        {
            if (other.returnClass != null)
                return false;
        }
        else if (!returnClass.equals(other.returnClass))
            return false;
        return true;
    }

    /**
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return String.format("Invocation [targetClass=%s, returnClass=%s, methodName=%s, methodArgs=%s]", targetClass, returnClass, methodName, Arrays.toString(methodArgs));
    }
}