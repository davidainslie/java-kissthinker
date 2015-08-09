package com.kissthinker.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author David Ainslie
 */
public class MethodUtilTest
{ 
    /** */
    public MethodUtilTest()
    {
        super();
    }

    /** */
	@Test
	public void acquireMethod()
	{
		Method method = MethodUtil.acquireMethod(new Pojo(), "method", new ArrayList<>());
		assertNotNull(method);
		assertTrue(method.getParameterTypes()[0].isInstance(new ArrayList<>()));

		method = MethodUtil.acquireMethod(new Pojo(), "method", new LinkedList<>());
		assertNotNull(method);
		assertTrue(method.getParameterTypes()[0].isInstance(new LinkedList<>()));
	}

	/** */
	@Test
	public void isGetter()
	{
		// assertTrue(MethodUtil.isGetter(MethodUtil.acquireMethod(new Pojo(), "decorated"))); TODO
		assertTrue(MethodUtil.isGetter(MethodUtil.acquireMethod(new Pojo(), "isDecorated")));
		assertFalse(MethodUtil.isGetter(MethodUtil.acquireMethod(new Pojo(), "method", new LinkedList<>())));
	}
}