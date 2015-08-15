package com.kissthinker.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

/**
 * @author David Ainslie
 */
public class ConstructorUtilTest
{  
    /** */
    public ConstructorUtilTest()
    {
        super();
    }

    /** */
	@Test
	public void getConstructor()
	{
		PojoInterface pojoInterface = new Pojo(new Pojo());
		assertNotNull(ConstructorUtil.constructor(pojoInterface, pojoInterface.decorated()));

		pojoInterface = new Pojo();
		assertNotNull(ConstructorUtil.constructor(pojoInterface));
	}

	/**
	 *
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Test
	public void getConstructorOverloaded() throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		Constructor<Pojo> constructor = ConstructorUtil.constructorByTypes(Pojo.class, Integer.class);
		System.out.println(constructor);

		constructor = ConstructorUtil.constructorByTypes(Pojo.class, Double.class);
		System.out.println(constructor);

		constructor = ConstructorUtil.constructorByTypes(Pojo.class, int.class);
		System.out.println(constructor);

		constructor = ConstructorUtil.constructorByTypes(Pojo.class, ArrayList.class, int.class);
		System.out.println(constructor);
		constructor.newInstance(new ArrayList<>(), new Integer(1));

		constructor = ConstructorUtil.constructorByTypes(Pojo.class, LinkedList.class, int.class);
		System.out.println(constructor);
	}

	/** */
	@Test
	public void newInstance()
	{
		PojoInterface pojoInterface = ConstructorUtil.newInstance(Pojo.class);
		assertNotNull(pojoInterface);

		pojoInterface = ConstructorUtil.newInstance(Pojo.class, new Pojo());
		assertNotNull(pojoInterface);
	}
}