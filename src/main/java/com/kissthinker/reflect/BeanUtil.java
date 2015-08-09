package com.kissthinker.reflect;

import java.util.Map;

/*import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;*/

/**
 * TODO This original uses Spring which as yet I have not added as a dependency
 * @author David Ainslie
 *
 */
public final class BeanUtil
{
    /**
     *
     * @param object
     * @return
     */
    public static Map<String, Object> propertyNamesAndValues(Object object)
    {
        return propertyNamesAndValues(object, false);
    }

    /**
     * TODO
     * @param object
     * @param convertEnumsToStrings
     * @return
     */
    public static Map<String, Object> propertyNamesAndValues(Object object, boolean convertEnumsToStrings)
    {
        /*Map<String, Object> propertyNamesAndValues = new HashMap<>();

        BeanWrapper objectBeanWrapper = new BeanWrapperImpl(object);

        for (PropertyDescriptor propertyDescriptor : objectBeanWrapper.getPropertyDescriptors())
        {
            if (propertyDescriptor.getPropertyType().isEnum() && convertEnumsToStrings)
            {
                final Object value = objectBeanWrapper.getPropertyValue(propertyDescriptor.getName());
                propertyNamesAndValues.put(propertyDescriptor.getName(), value != null ? value.toString() : null);
            }
            else
            {
                propertyNamesAndValues.put(propertyDescriptor.getName(), objectBeanWrapper.getPropertyValue(propertyDescriptor.getName()));
            }
        }

        return propertyNamesAndValues;*/
        
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     * @param object
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> describe(Object object)
    {
        /*try
        {
            Map<String, Object> objectKeyValueProperties = BeanUtils.describe(object);

            // Remove unwanted properties.
            objectKeyValueProperties.remove("class");

            objectKeyValueProperties.remove("first");
            objectKeyValueProperties.remove("last");
            objectKeyValueProperties.remove("empty");

            objectKeyValueProperties.remove("observers");
            objectKeyValueProperties.remove("propertyChanges");

            return objectKeyValueProperties;
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }*/
        
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     * @param bean
     * @param propertyName
     * @param propertyValue
     */
    public static void setProperty(Object bean, String propertyName, Object propertyValue)
    {
        /*try
        {
            BeanUtils.setProperty(bean, propertyName, propertyValue);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }*/
        
        throw new UnsupportedOperationException();
    }

    /**
     * Utility.
     */
    private BeanUtil()
    {
        super();
    }
}