package com.kissthinker.reflect;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
// import com.kissthinker.property.Property; TODO

/**
 * @author David Ainslie
 */
public class Pojo implements PojoInterface
{
	private static final Logger LOGGER = Logger.getLogger("Pojo");

	/*@Property TODO*/ private PojoInterface decorated;

	/** */
	public Pojo()
	{
	    super();
	}

	/** */
	public Pojo(Integer integer)
	{
	}

	/** */
	public Pojo(Double double_)
	{
	}

	/** */
	public Pojo(int int_)
	{
	}

	/** */
	public Pojo(List<Integer> list, int int_)
	{
	}

	/** */
	public Pojo(ArrayList<Integer> list, int int_)
	{
	}

	/** */
	public Pojo(PojoInterface decorated)
	{
		this.decorated = decorated;
	}

	/** */
	@Override
	public PojoInterface decorated()
	{
		return decorated;
	}

	/** */
	public boolean isDecorated()
	{
		if (decorated == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	/** */
	public void method(List<String> strings)
	{
		LOGGER.info("method(List<String>)");
	}

	/** */
	public void method(ArrayList<String> strings)
	{
		LOGGER.info("method(ArrayList<String>)");
	}
}