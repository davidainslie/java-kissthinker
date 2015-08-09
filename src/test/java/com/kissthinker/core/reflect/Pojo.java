package com.kissthinker.core.reflect;

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

	/**
	 *
	 * @param integer
	 */
	public Pojo(Integer integer)
	{
	}

	/**
	 *
	 * @param double_
	 */
	public Pojo(Double double_)
	{
	}

	/**
	 *
	 * @param int_
	 */
	public Pojo(int int_)
	{
	}

	/**
	 *
	 * @param list
	 * @param int_
	 */
	public Pojo(List<Integer> list, int int_)
	{
	}

	/**
	 *
	 * @param list
	 * @param int_
	 */
	public Pojo(ArrayList<Integer> list, int int_)
	{
	}

	/**
	 *
	 * @param decorated
	 */
	public Pojo(PojoInterface decorated)
	{
		this.decorated = decorated;
	}

	/**
	 *
	 * @see com.kissthinker.PojoInterface#decorated()
	 */
	@Override
	public PojoInterface decorated()
	{
		return decorated;
	}

	/**
	 *
	 * @return
	 */
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

	/**
	 *
	 * @param strings
	 */
	public void method(List<String> strings)
	{
		LOGGER.info("method(List<String>)");
	}

	/**
	 *
	 * @param strings
	 */
	public void method(ArrayList<String> strings)
	{
		LOGGER.info("method(ArrayList<String>)");
	}
}