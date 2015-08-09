package com.kissthinker.coder;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kissthinker.factory.Factory;

/**
 * A {@link Coder} version of an {@link InputStream} where objects are read (much like an {@link ObjectInputStream} but as a standard stream of bytes for decoding.
 * <br/>
 * Note that at the other end of the communication channel a {@link CoderOutputStream} should be used.<p/>
 * @author David Ainslie
 *
 */
public class CoderInputStream extends InputStream
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(CoderInputStream.class);

    /** */
    private final DataInputStream dataInputStream;

    /**
     *
     * @param inputStream
     */
    public CoderInputStream(InputStream inputStream)
    {
        super();
        this.dataInputStream = new DataInputStream(inputStream);
    }

    /**
     * @see java.io.InputStream#read()
     */
    @Override
    public int read() throws IOException
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param <I>
     * @param <O>
     * @return
     * @throws IOException
     */
    public <I, O> Identifiable<I, O> readObject() throws IOException
    {
        return decodeIdentifiable(decodeCoder());
    }

    /**
     * @return
     * @throws IOException
     */
    private Coder decodeCoder() throws IOException
    {
        // Read in coder via 2 byte prefix depicting length.
        int coderLength = dataInputStream.readUnsignedShort();

        byte[] coderBytes = new byte[coderLength];
        dataInputStream.readFully(coderBytes);

        Coder coder = Factory.create(new String(coderBytes)); // TODO Should cache coder bytes
        return coder;
    }

    /**
     * @param coder
     * @return
     * @throws IOException
     */
    private <I, O> Identifiable<I, O> decodeIdentifiable(Coder coder) throws IOException
    {
        // Read in identifiable via 2 byte prefix depicting length and decode using given coder.
        int decodeLength = dataInputStream.readUnsignedShort();

        byte[] decodeBytes = new byte[decodeLength];
        dataInputStream.readFully(decodeBytes);

        return coder.decode(decodeBytes);
    }
}