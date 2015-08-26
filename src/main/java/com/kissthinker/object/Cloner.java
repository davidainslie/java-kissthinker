package com.kissthinker.object;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import com.kissthinker.io.ByteOutputStream;

/**
 * Object cloning utility.
 * <p>
 * @author David Ainslie
 *
 */
@Singleton
public final class Cloner
{
    /**
     * Clone by serialization, making use of "fast" output/input byte array streams.
     * @param <O> Object type
     * @param object a Serializable object to be cloned. All references must be Serializable (client should set transient appropriately).
     * @return O the cloned object
     */
    public static <O extends Serializable> O clone(O object)
    {
        try
        {
            // Write object out to a byte array.
            ByteOutputStream byteOutputStream = new ByteOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            objectOutputStream.close();

            // Retrieve input stream from byte array and read a copy of object back in.
            ObjectInputStream objectInputStream = new ObjectInputStream(byteOutputStream.inputStream());

            @SuppressWarnings("unchecked")
            O clone = (O)objectInputStream.readObject();

            return clone;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Utility.
     */
    private Cloner()
    {
        super();
    }
}