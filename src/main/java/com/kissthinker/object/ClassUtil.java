package com.kissthinker.object;

/**
 * Utility methods for Class.
 * <p>
 * @author David Ainslie
 *
 */
public final class ClassUtil
{
    /**
     *
     * @param <C>
     * @param pathAndClassName
     * @return
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static <C> Class<C> loadClass(String pathAndClassName) throws ClassNotFoundException
    {
        try
        {
            return (Class<C>)Thread.currentThread().getContextClassLoader().loadClass(pathAndClassName);
        }
        catch (Exception e)
        {
            return (Class<C>)Class.forName(pathAndClassName);
        }
    }

    /**
     *
     * @param object
     * @return String name of given object exluding package(s).
     */
    public static String name(Object object)
    {
        return name(object.getClass());
    }

    /**
     * Get path of given class e.g given class com.kissthinker.object.ClassUtil to get back com/kissthinker/object
     * @param class_
     * @return String of path to given class
     */
    public static String path(Class<?> class_)
    {
        return class_.getPackage().getName().replaceAll("\\.", "/");
    }

    /**
     *
     * @param class_
     * @return
     */
    public static String pathAndClassName(Class<?> class_)
    {
        return class_.getName().replaceAll("\\.", "/");
    }

    /**
     * Get path of given class with a prefix.
     * @param prefix
     * @param class_
     * @return String of path to given class
     */
    public static String path(String prefix, Class<?> class_)
    {
        return prefix + path(class_);
    }

    /**
     *
     * @param prefix
     * @param class_
     * @return
     */
    public static String pathAndClassName(String prefix, Class<?> class_)
    {
        return prefix + pathAndClassName(class_);
    }

    /**
     * Get path of given class with a suffix.
     * @param class_
     * @param suffix
     * @return String of path to given class
     */
    public static String path(Class<?> class_, String suffix)
    {
        return path(class_) + suffix;
    }

    /**
     *
     * @param class_
     * @param suffix
     * @return
     */
    public static String pathAndClassName(Class<?> class_, String suffix)
    {
        return pathAndClassName(class_) + suffix;
    }

    /**
     * Get path of given class with a prefix and suffix.
     * @param prefix
     * @param class_
     * @param suffix
     * @return String of path to given class
     */
    public static String path(String prefix, Class<?> class_, String suffix)
    {
        return prefix + path(class_) + suffix;
    }

    /**
     *
     * @param prefix
     * @param class_
     * @param suffix
     * @return
     */
    public static String pathAndClassName(String prefix, Class<?> class_, String suffix)
    {
        return prefix + pathAndClassName(class_) + suffix;
    }

    /**
     *
     * @param class_
     * @return String name of given class_ exluding package(s).
     */
    public static String name(Class<?> class_)
    {
        return class_.getSimpleName();
    }

    /**
     * Utility.
     */
    private ClassUtil()
    {
        super();
    }
}