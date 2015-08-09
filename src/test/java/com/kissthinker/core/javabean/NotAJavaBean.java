package com.kissthinker.core.javabean;

import java.io.Serializable;

/**
 * @author David Ainslie
 *
 */
public class NotAJavaBean implements Serializable
{
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    @Property
    private String id = "Scooby";

    /** */
    public NotAJavaBean()
    {
        super();
    }

    /**
     * Getter
     * @return the id
     */
    public String id()
    {
        return id;
    }

    /**
     * Setter
     * @param id the id to set
     */
    public void id(String id)
    {
        this.id = id;
    }
}