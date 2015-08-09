package com.kissthinker.reflect;

import java.io.Serializable;
// import com.kissthinker.property.PropertySupport;

/**
 * @author David Ainslie
 */
public class Woman implements Serializable
{
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    private Address home;

    /** */
    public Woman()
    {
        super();
    }

    /**
     *
     * @param home
     */
    public Woman(Address home)
    {
        this.home = home;
    }

    /** */
    public Address home()
    {
        return home;
    }

    /**
     *
     * @param home
     */
    public Woman home(Address home)
    {
        //PropertySupport.set(this, "home", home);
        return this;
    }
}