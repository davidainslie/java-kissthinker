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

	/** */
	public Pojo1(PojoInterface decorated)
	{
		this.decorated = decorated;
	}

	/** */
	@Override
	public PojoInterface decorated()
	{
		return decorated;
	}
}