package com.kissthinker.reflect;

import java.io.Serializable;
// import com.ddtechnology.property.PropertySupport; TODO

/**
 * @author David Ainslie
 */
public class Address implements Serializable
{
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    private String street;
    
    /** */
    private String telephone;

    /** */
    public Address()
    {
        super();
    }

    /**
     *
     * @param street
     * @param telephone
     */
    public Address(String street, String telephone)
    {
        this.street = street;
        this.telephone = telephone;
    }

    /**
     * Getter
     * @return
     */
    public String street()
    {
        return street;
    }

    /**
     * Setter
     * @param street
     */
    public Address setStreet(String street)
    {
        // PropertySupport.set(this, "street", street); TODO
        return this;
    }

    /**
     * Getter
     * @return
     */
    public String telephone()
    {
        return telephone;
    }

    /**
     * Setter
     * @param telephone
     */
    public Address telephone(String telephone)
    {
        // PropertySupport.set(this, "telephone", telephone); TODO
        return this;
    }
}