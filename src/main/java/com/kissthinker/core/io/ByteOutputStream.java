package com.kissthinker.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * To be fast(er), this {@link OutputStream} implementation does not synchronize.
 * <br/>
 * @author David Ainslie
 *
 */
public class ByteOutputStream extends OutputStream
{
    /** */
    private byte[] buffer;

    /** */
    private int size;

    /**
     * Constructs stream with default buffer capacity 5K
     */
    public ByteOutputStream()
    {
        this(5 * 1024);
    }

    /**
     * Constructs stream with given buffer size.
     * @param bufferSize
     */
    public ByteOutputStream(int bufferSize)
    {
        super();
        buffer = new byte[bufferSize];
    }

    /**
     *
     * @return InputStream for reading back the written data
     */
    public InputStream inputStream()
    {
        return new ByteInputStream(buffer, size);
    }

    /**
     *
     * @see java.io.OutputStream#write(int)
     */
    @Override
    public void write(int b) throws IOException
    {
        verifyBufferSize(size + 1);
        buffer[size++] = (byte)b;
    }

    /**
     *
     * @see java.io.OutputStream#write(byte[])
     */
    @Override
    public void write(byte[] b) throws IOException
    {
        verifyBufferSize(size + b.length);
        System.arraycopy(b, 0, buffer, size, b.length);
        size += b.length;
    }

    /**
     *
     * @see java.io.OutputStream#write(byte[], int, int)
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException
    {
        verifyBufferSize(size + len);
        System.arraycopy(b, off, buffer, size, len);
        size += len;
    }

    /**
     *
     * @param newSize
     */
    private void verifyBufferSize(int newSize)
    {
        if (newSize > buffer.length)
        {
            byte[] old = buffer;
            buffer = new byte[Math.max(newSize, 2 * buffer.length)];
            System.arraycopy(old, 0, buffer, 0, old.length);
        }
    }
}