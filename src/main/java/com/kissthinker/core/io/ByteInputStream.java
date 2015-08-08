package com.kissthinker.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * To be fast(er), this {@link InputStream} implementation does not synchronize.
 * @author David Ainslie
 *
 */
public class ByteInputStream extends InputStream
{
    /** */
    private byte[] buffer;

    /** Number of bytes that can be read from {@link #buffer} */
    private int count;

    /** Number of bytes that have been read from {@link #buffer} */
    private int position;

    /**
     *
     * @param buffer
     * @param count
     */
    public ByteInputStream(byte[] buffer, int count)
    {
        super();
        this.buffer = buffer;
        this.count = count;
    }

    /**
     *
     * @see java.io.InputStream#read()
     */
    @Override
    public int read() throws IOException
    {
        return position < count ? buffer[position++] & 0xff : -1;
    }

    /**
     *
     * @see java.io.InputStream#read(byte[], int, int)
     */
    @Override
    public int read(byte[] b, int off, int len) throws IOException
    {
        if (position >= count)
        {
            return -1;
        }

        if (position + len > count)
        {
            len = count - position;
        }

        System.arraycopy(buffer, position, b, off, len);
        position += len;

        return len;
    }

    /**
     *
     * @see java.io.InputStream#available()
     */
    @Override
    public int available() throws IOException
    {
        return count - position;
    }

    /**
     *
     * @see java.io.InputStream#skip(long)
     */
    @Override
    public long skip(long n) throws IOException
    {
        if (position + n > count)
        {
            n = count - position;
        }

        if (n < 0)
        {
            return 0;
        }

        position += n;

        return n;
    }
}