package com.kissthinker.core.reflect;

import java.lang.reflect.Field;
import org.junit.Test;
import com.kissthinker.core.javabean.Bean;
import com.kissthinker.core.javabean.Property;
import com.kissthinker.core.javabean.SubBean;
import static org.junit.Assert.assertEquals;

/**
 * @author David Ainslie
 */
public class FieldUtilTest
{
    /** */
    @Test
    public void annotatedFields()
    {
        Field[] propertyFields = FieldUtil.fields(Bean.class, Property.class);
        assertEquals(2, propertyFields.length);
        
        propertyFields = FieldUtil.fields(SubBean.class, Property.class);
        assertEquals(3, propertyFields.length);
    }
}