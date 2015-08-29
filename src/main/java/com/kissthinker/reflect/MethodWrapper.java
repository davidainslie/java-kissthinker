package com.kissthinker.reflect;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author David Ainslie
 *
 */
public class MethodWrapper
{
    /** */
    private final Class<?> returnType;

    /** */
    private final String name;

    /** */
    private final Class<?>[] paramaterTypes;

    /** */
    private final Class<?>[] exceptionTypes;

    /**
     *
     * @param method Method
     */
    public MethodWrapper(Method method)
    {
        this(method.getReturnType(), method.getName(), method.getParameterTypes(), method.getExceptionTypes());
    }

    /**
     *
     * @param returnType Return type
     * @param name Name
     * @param parameterTypes Parameter types
     * @param exceptionTypes Exception types
     */
    public MethodWrapper(Class<?> returnType, String name, Class<?>[] parameterTypes, Class<?>[] exceptionTypes)
    {
        this.returnType = returnType;
        this.name = name;
        this.paramaterTypes = parameterTypes;
        this.exceptionTypes = exceptionTypes;
    }

    /**
     *
     * @return Class
     */
    public Class<?> returnType()
    {
        return returnType;
    }

    /**
     *
     * @return Name
     */
    public String name()
    {
        return name;
    }

    /**
     *
     * @return Class array
     */
    public Class<?>[] parameterTypes()
    {
        return paramaterTypes.clone();
    }

    /**
     *
     * @return Class array
     */
    public Class<?>[] exceptionTypes()
    {
        return exceptionTypes.clone();
    }

    /**
     *
     * @param other Object
     * @return boolean
     */
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof MethodWrapper))
        {
            return false;
        }

        MethodWrapper methodWrapper = (MethodWrapper)other;

        return name.equals(methodWrapper.name) &&
               returnType.equals(methodWrapper.returnType) &&
               Arrays.equals(paramaterTypes, methodWrapper.paramaterTypes);
    }

    /**
     *
     * @return Hashcode
     */
    @Override
    public int hashCode()
    {
        return name.hashCode() + returnType.hashCode() + Arrays.hashCode(paramaterTypes);
    }

    /**
     *
     * @return String
     */
    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(returnType.getName()).append(" ").append(name).append("(");
        String comma = "";

        for (Class<?> paramType : paramaterTypes)
        {
            stringBuilder.append(comma).append(paramType.getName());
            comma = ", ";
        }

        stringBuilder.append(")");

        return stringBuilder.toString();
    }
}