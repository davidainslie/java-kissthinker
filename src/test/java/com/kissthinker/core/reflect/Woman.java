package com.kissthinker.core.reflect;


import java.io.Serializable;

// import com.ddtechnology.property.PropertySupport;


/**
 * @author David Ainslie
 */
public class Woman implements Serializable
{
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    private Address home;


    /**
     *
     */
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


    /**
     *
     * @return
     */
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