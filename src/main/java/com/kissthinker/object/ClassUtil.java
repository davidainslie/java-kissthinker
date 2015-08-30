package com.kissthinker.object;

/**
 * Utility methods for Class.
 *
 * @author David Ainslie
 *
 */
@Singleton
public final class ClassUtil
{
    /**
     *
     * @param <C> Class type
     * @param pathAndClassName Path and class name
     * @return Instance of class
     * @throws ClassNotFoundException As is says on the tin
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
     * @param object Object
     * @return String name of given object exluding package(s).
     */
    public static String name(Object object)
    {
        return name(object.getClass());
    }

    /**
     * Get path of given class e.g given class com.kissthinker.object.ClassUtil to get back com/kissthinker/object
     * @param class_ Class
     * @return String of path to given class
     */
    public static String path(Class<?> class_)
    {
        return class_.getPackage().getName().replaceAll("\\.", "/");
    }

    /**
     *
     * @param class_ Class
     * @return Path and class name
     */
    public static String pathAndClassName(Class<?> class_)
    {
        return class_.getName().replaceAll("\\.", "/");
    }

    /**
     * Get path of given class with a prefix.
     * @param prefix Prefix
     * @param class_ Class
     * @return String of path to given class
     */
    public static String path(String prefix, Class<?> class_)
    {
        return prefix + path(class_);
    }

    /**
     *
     * @param prefix Prefix
     * @param class_ Class
     * @return Path and class name
     */
    public static String pathAndClassName(String prefix, Class<?> class_)
    {
        return prefix + pathAndClassName(class_);
    }

    /**
     * Get path of given class with a suffix.
     * @param class_ Class
     * @param suffix Suffix
     * @return String of path to given class
     */
    public static String path(Class<?> class_, String suffix)
    {
        return path(class_) + suffix;
    }

    /**
     *
     * @param class_ Class
     * @param suffix Suffix
     * @return Path and class name
     */
    public static String pathAndClassName(Class<?> class_, String suffix)
    {
        return pathAndClassName(class_) + suffix;
    }

    /**
     * Get path of given class with a prefix and suffix.
     * @param prefix Prefix
     * @param class_ Class
     * @param suffix Suffix
     * @return String of path to given class
     */
    public static String path(String prefix, Class<?> class_, String suffix)
    {
        return prefix + path(class_) + suffix;
    }

    /**
     *
     * @param prefix Prefix
     * @param class_ Class
     * @param suffix Suffix
     * @return Path and class name
     */
    public static String pathAndClassName(String prefix, Class<?> class_, String suffix)
    {
        return prefix + pathAndClassName(class_) + suffix;
    }

    /**
     *
     * @param class_ Class
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