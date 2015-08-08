package com.kissthinker.core.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author David Ainslie
 */
public final class FieldUtil
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(FieldUtil.class);

    /**
     *
     * @param <O>
     * @param object
     * @param annotationClass
     * @return
     */
    public static <O> Field field(O object, Class<? extends Annotation> annotationClass)
    {
        Field[] fields = fields(object, annotationClass);

        if (fields == null)
        {
            return null;
        }
        else if (fields.length == 1)
        {
            return fields[0];
        }
        else
        {
            throw new FieldException(String.format("Found %s occurences of required annotation %s in object %s, when there should be only one!",
                                                   fields.length, annotationClass, object));
        }
    }

    /**
     *
     * @param <O>
     * @param object
     * @param fieldName
     * @param annotationClass
     * @return
     */
    public static <O> Field field(O object, String fieldName, Class<? extends Annotation> annotationClass)
    {
        Field[] fields = fields(object, annotationClass);

        if (fields != null)
        {
            for (Field field : fields)
            {
                field.setAccessible(true);
                
                if (field.getName().equals(fieldName))
                {
                    return field;
                }
            }
        }

        return null;
    }

    /**
     *
     * @param object
     * @param annotationClass
     * @return
     */
    public static <O> Field[] fields(O object, Class<? extends Annotation> annotationClass)
    {
        assert(object != null);
        
        // Safety check, in case (somehow) this method was called with a given class (maybe by reflection).
        if (object instanceof Class<?>)
        {
            return fields((Class<?>)object, annotationClass);
        }
        
        return fields(object.getClass(), annotationClass);
    }

    /**
     * 
     * @param class_
     * @param annotationClass
     * @return
     */
    public static <O> Field[] fields(Class<?> class_, Class<? extends Annotation> annotationClass)
    {
        assert(class_ != null);

        class_ = declaredClass(class_);

        List<Field> fields = new ArrayList<>();

        if (class_.getSuperclass() != null)
        {
            List<Field> superFields = new ArrayList<>();
            
            for (Field field : fields(class_.getSuperclass(), annotationClass))
            {
                superFields.add(field);
            }
            
            fields.addAll(superFields);
        }
        
        for (Field field : class_.getDeclaredFields())
        {
            field.setAccessible(true);
            
            if (field.isAnnotationPresent(annotationClass))
            {
                fields.add(field);
            }
        }

        return fields.toArray(new Field[0]);
    }

    /**
     *
     * @param object
     * @param annotationClass
     * @return
     */
    public static Object[] values(Object object, Class<? extends Annotation> annotationClass)
    {
        try
        {
            Field[] fields = fields(object, annotationClass);

            List<Object> objects = new ArrayList<>();

            for (Field field : fields)
            {
                field.setAccessible(true);
                objects.add(field.get(object));
            }

            return objects.toArray(new Object[0]);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
            throw new FieldException(e);
        }
    }

    /**
     * 
     * @param field
     * @param target
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <V> V value(Field field, Object target)
    {
        try
        {
            field.setAccessible(true);
            return (V)field.get(target);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Get declared class that is not an anonymous class;
     * @param class_
     * @return
     */
    public static Class<?> declaredClass(Class<?> class_)
    {
        if (class_.getName().contains("$"))
        {
            LOGGER.info("Anonymous class so aquiring 'superclass'");
            return declaredClass(class_.getSuperclass());
        }
        else
        {
            return class_;
        }
    }

    /**
     *
     * @param object
     * @param name
     * @param value
     */
    public static void set(Object object, String name, Object value)
    {
        Field field = null;

        try
        {
            field = object.getClass().getField(name);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
        }

        if (field == null)
        {
            try
            {
                field = object.getClass().getDeclaredField(name);
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
            }
        }

        if (field != null)
        {
            field.setAccessible(true);

            try
            {
                System.out.println(field.get(object));
                field.set(object, value);
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
            }
        }
    }

    /**
     * Utility.
     */
    private FieldUtil()
    {
        super();
    }
}