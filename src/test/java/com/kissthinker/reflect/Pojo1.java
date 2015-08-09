package com.kissthinker.reflect;

/**
 * @author David Ainslie
 */
public class Pojo1 implements PojoInterface
{
    /** */
	private PojoInterface decorated;

	/** */
	public Pojo1()
	{
	    super();
	}

	/**
	 *
	 * @param decorated
	 */
	public Pojo1(PojoInterface decorated)
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
}