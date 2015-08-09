package com.kissthinker.morph.ezmorph;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Morph a String, representing a server socket port to a {@link ServerSocket}
 * <br/>
 * This implementation makes the assumption, that only one {@link ServerSocket} will be created for running JVM.<br/>
 * TODO Allow for server sockets to be created on other ports.
 * @author David Ainslie
 *
 */
public class StringToServerSocketMorpher extends AbstractObjectMorpher
{
    /** */
    private static final Lock LOCK = new ReentrantLock();

    /** Only allow one server socket to be created (morphed from a port). */
    private static ServerSocket serverSocket;

    /** */
    public StringToServerSocketMorpher()
    {
        super(String.class, ServerSocket.class);
    }

    /**
     *
     * @see net.sf.ezmorph.ObjectMorpher#morph(java.lang.Object)
     */
    @Override
    public Object morph(Object object)
    {
        try
        {
            LOCK.lock();

            if (serverSocket != null)
            {
                return serverSocket;
            }

            Integer port = Integer.valueOf(object.toString());

            return serverSocket = new ServerSocket(port);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            LOCK.unlock();
        }
    }
}