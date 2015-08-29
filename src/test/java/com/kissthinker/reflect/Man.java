package com.kissthinker.reflect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//import com.ddtechnology.property.PropertySupport;

/**
 * @author David Ainslie
 */
public class Man implements Serializable
{
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    private Woman wife;
    
    /** */
    private List<Address> homes;
    
    /** */
    private Address home;

    /** */
    public Man()
    {
        super();
    }

    /**
     *
     * @param wife Wife
     * @param homes Homes
     */
    public Man(Woman wife, List<Address> homes)
    {
        this.wife = wife;
        homes(homes);
    }

    /** */
    public Woman wife()
    {
        return wife;
    }

    /**
     *
     * @param wife Wife
     */
    public Man wife(Woman wife)
    {
        //PropertySupport.set(this, "wife", wife);
        return this;
    }

    /** */
    public List<Address> homes()
    {
        return homes;
    }

    /**
     * Default first home as home then others are secondary homes e.g. holiday homes.
     * @param homes Homes
     */
    public Man homes(List<Address> homes)
    {
        //PropertySupport.set(this, "homes", homes);

        if (homes != null && !homes.isEmpty())
        {
            home = homes.get(0);
        }
        
        return this;
    }

    /** */
    public Address home()
    {
        return home;
    }

    /**
     *
     * @param home Home
     */
    public Man home(Address home)
    {
        //PropertySupport.set(this, "home", home);

        if (homes == null)
        {
            homes = new ArrayList<>();
            homes.add(home);
        }
        else if (homes.contains(home))
        {
            int index = homes.indexOf(home);
            homes.add(index, home);
        }
        else
        {
            homes.add(home);
        }
        
        return this;
    }
}