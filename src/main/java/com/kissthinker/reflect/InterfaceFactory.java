package com.kissthinker.reflect;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO
// import com.kissthinker.compiler.ClassFactory;

/**
 * TODO
 * @author David Ainslie
 *
 */
public final class InterfaceFactory
{
    /**
     *
     * @param class_
     * @return
     */
    public static Class<?> create(Class<?> class_)
    {
        String interfaceName = class_.getName() + "$Interface";
        InterfaceClassLoader interfaceClassLoader = new InterfaceClassLoader();

        try
        {
            return Class.forName(interfaceName, false, interfaceClassLoader);
        }
        catch (ClassNotFoundException e)
        {
            // We have to dynamically create the interface.
            Set<MethodWrapper> methodWrappers = new LinkedHashSet<>();

            for (Method method : class_.getMethods())
            {
                if (!"getClass".equals(method.getName()) && !"equals".equals(method.getName()) && !"hashCode".equals(method.getName()) &&
                    !"wait".equals(method.getName()) && !"notify".equals(method.getName()) && !"notifyAll".equals(method.getName()))
                {
                    methodWrappers.add(new MethodWrapper(method));
                }
            }

            if (methodWrappers.isEmpty())
            {
                throw new IllegalArgumentException("Class has no methods!: " + class_);
            }

            return interfaceClassLoader.findOrCreateInterface(interfaceName, methodWrappers.toArray(new MethodWrapper[0]));
        }
    }

    /**
     * Utility.
     */
    private InterfaceFactory()
    {
    }

    /**
     *
     * @author David Ainslie
     *
     */
    private static class InterfaceClassLoader extends ClassLoader
    {
        /**
         *
         */
        public InterfaceClassLoader()
        {
            super(Thread.currentThread().getContextClassLoader());
        }

        /**
         *
         * @param name
         * @param methodWrappers
         * @return
         */
        public Class<?> findOrCreateInterface(String name, MethodWrapper... methodWrappers)
        {
            Class<?> class_ = findLoadedClass(name);

            if (class_ == null)
            {
                try
                {
                    return create(name, methodWrappers);
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    throw new RuntimeException(e);
                }
            }
            else
            {
                return class_;
            }
        }

        /**
         *
         * @param name
         * @param methodWrappers
         * @return
         * @throws Exception
         */
        private Class<?> create(String name, MethodWrapper... methodWrappers) throws Exception
        {
            Matcher matcher = Pattern.compile("(.*)(\\.)(.*)").matcher(name);

            if (matcher.find())
            {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("package %s;\n", matcher.group(1)));
                sb.append(String.format("public interface %s {\n", matcher.group(3)));
                sb.append(createJavaSource(methodWrappers));
                sb.append("}");

                // return ClassFactory.create(name, sb.toString());
                throw new UnsupportedOperationException(); // TODO
            }
            else
            {
                throw new RuntimeException("What the...");
            }
        }

        /**
         *
         * @param methodWrappers
         * @return
         */
        private String createJavaSource(MethodWrapper... methodWrappers)
        {
            StringBuilder sb = new StringBuilder();

            for (MethodWrapper methodWrapper : methodWrappers)
            {
                sb.append("public abstract ").append(methodWrapper.returnType().getName()).append(" ").append(methodWrapper.name()).append("(");

                if (methodWrapper.parameterTypes() != null)
                {
                    for (int i = 0; i < methodWrapper.parameterTypes().length; i++)
                    {
                        if (i > 0)
                        {
                            sb.append(", ");
                        }

                        sb.append(methodWrapper.parameterTypes()[i].getName()).append(" arg").append(i);
                    }
                }

                sb.append(")");

                if (methodWrapper.exceptionTypes() != null)
                {
                    for (int i = 0; i < methodWrapper.exceptionTypes().length; i++)
                    {
                        if (i == 0)
                        {
                            sb.append(" throws ");
                        }
                        else
                        {
                            sb.append(", ");
                        }

                        sb.append(methodWrapper.exceptionTypes()[i].getName());
                    }
                }

                sb.append(";\n");
            }

            return sb.toString();
        }
    }
}