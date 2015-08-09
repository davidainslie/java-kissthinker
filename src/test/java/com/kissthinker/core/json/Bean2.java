package com.kissthinker.core.json;

import java.awt.Dimension;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author David Ainslie
 *
 */
public class Bean2
{
    /** */
    private URI uri;

    /** */
    private Dimension dimension = new Dimension(50, 75);

    /** */
    public Bean2()
    {
        super();

        try
        {
            uri = new URI("http://www.kissthinker.com");
        }
        catch (URISyntaxException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Bean2 [uri=" + uri + ", dimension=" + dimension + "]";
    }
}