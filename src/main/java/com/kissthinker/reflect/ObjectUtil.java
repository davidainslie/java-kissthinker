package com.kissthinker.reflect;

import java.io.*;

/*
import org.jboss.serial.io.JBossObjectOutputStream;
import com.kissthinker.UsageWarning;
import com.kissthinker.property.Property;
*/

/**
 * Utility for object (and class) manipulation.
 *
 * TODO Consolidate with another version
 * @author David Ainslie
 */
public abstract class ObjectUtil
{
	/**
	 * This is a naughty (generics) cast that avoids 2 issues.<br>
	 * 1) Having to write a cast in front of an "object", where the cast is already implied by the assigned variable e.g.
	 * Map map = (Map)someObject;
	 * But the variable "map" already implies the type so why write it all out again.
	 * Instead do:
	 * import static com.ddtechnology.reflect.ObjectUtils.cast;
	 * Map map = cast(someObject);
	 * 2) Prevent the "unchecked" warning because of "generics casting".
	 * Of course this actually implies that your code "smells", but we are all guilty of some hacking and sometimes you just don't want the warning.
	 * @param <O> Object type
	 * @param object Object
	 * @return Object
	 */
	//@UsageWarning
	@SuppressWarnings("unchecked")
	public static <O> O cast(Object object)
	{
		return (O)object;
	}

    /**
     *
     * @param object Object
     * @return name class name only i.e. without package name.
     */
    public static String className(Object object)
    {
        if (object == null)
        {
            return null;
        }

        String fullClassName = null;

        if (object instanceof Class<?>)
        {
            fullClassName = object.toString();
        }
        else
        {
            fullClassName = object.getClass().getName();
        }

        int lastDotIndex = fullClassName.lastIndexOf(".");

        return fullClassName.substring(lastDotIndex + 1);
    }

    /**
     *
     * @param className Class name
     * @return String
     */
    public static String className(String className)
    {
        int lastDotIndex = className.lastIndexOf(".");

        return className.substring(lastDotIndex + 1);
    }

    /**
     *
     * @param object Object
     * @return name class name along with its package. Note that this method will produce e.g. "com.ddtechnology.SomeClass"
     */
    public static String fullClassName(Object object)
    {
        if (object == null)
        {
            return null;
        }

        if (object instanceof Class<?>)
        {
            return object.toString().replaceAll("class", "").trim();
        }
        else
        {
            return object.getClass().getName().trim();
        }
    }

    /**
     *
     * @param object Object
     * @return Package name
     */
    public static String packageName(Object object)
    {
        if (object == null)
        {
            return null;
        }
        else
        {
            return packageName(object.getClass());
        }
    }

    /**
     *
     * @param class_ Class
     * @return Package name
     */
    public static String packageName(Class<?> class_)
    {
        if (class_ == null)
        {
            return null;
        }
        else
        {
            return class_.getPackage().getName();
        }
    }

    /**
     *
     * @param object1 Object
     * @param object2 Object
     * @return name class name along with its package. Note that this method will produce e.g. "com.ddtechnology.SomeClass"
     */
    public static boolean isSameClass(Object object1, Object object2)
    {
        return fullClassName(object1).equals(fullClassName(object2));
    }

    /**
     *
     * @param class1 Class
     * @param class2 class
     * @return True of false
     */
    public static boolean isSameClass(Class<?> class1, Class<?> class2)
    {
        return class1.getName().equals(class2.getName());
    }

    /**
     *
     * @param <O> Object type
     * @param object of which a deep (complete) copy is performed and returned - note that the given object must be serializable.
     * @return deep copy of given object.
     */
    @SuppressWarnings("unchecked")
    public static <O extends Serializable> O deepCopy(O object)
    {
        try
        {
            // Write the object out to a byte array.
            FastByteArrayOutputStream fbos = new FastByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(fbos);
            out.writeObject(object);
            out.flush();
            out.close();

            // Retrieve an input stream from the byte array and read a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(fbos.getInputStream());

            return (O)in.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param <O> Object type
     * @param object Object
     * @return Object
     */
    @SuppressWarnings("unchecked")
    public static <O extends Serializable> O deepCopyViaJBoss(O object)
    {
        /*try
        {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JBossObjectOutputStream jBossObjectOutputStream = new JBossObjectOutputStream(byteArrayOutputStream);

            return (O)jBossObjectOutputStream.smartClone(object);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }*/
        
        throw new UnsupportedOperationException();
    }

    /**
     * Does "container" have a reference (direct or indirect) to a given object that is a @Property
     * @param container Container
     * @param object Object
     * @return True or false
     */
    public static boolean hasProperty(Object container, Object object)
    {
        /*Object[] propertyObjects = FieldUtil.getValues(container, Property.class);

        for (Object propertyObject : propertyObjects)
        {
            if (propertyObject.equals(object))
            {
                return true;
            }
            else
            {
                // Drill down.
                if (hasProperty(propertyObject, object))
                {
                    return true;
                }
            }
        }

        return false;*/
        
        throw new UnsupportedOperationException();
    }
}

/**
 * ByteArrayOutputStream implementation that doesn't synchronize methods and doesn't copy the data on toByteArray().
 * @author David Ainslie
 */
class FastByteArrayOutputStream extends OutputStream
{
    /** */
    private byte[] buffer;
    
    /** */
    private int size;

    /**
     * @see java.io.OutputStream#write(byte[])
     * @param bytes array of bytes
     */
    @Override
    public void write(byte bytes[])
    {
        verifyBufferSize(size + bytes.length);
        System.arraycopy(bytes, 0, buffer, size, bytes.length);
        size += bytes.length;
    }

    /**
     * @see java.io.OutputStream#write(byte[], int, int)
     * @param bytes array of bytes
     * @param offSet Offset
     * @param length Length
     */
    @Override
    public void write(byte bytes[], int offSet, int length)
    {
        verifyBufferSize(size + length);
        System.arraycopy(bytes, offSet, buffer, size, length);
        size += length;
    }

    /**
     * @see java.io.OutputStream#write(int)
     * @param aByte A byte
     */
    @Override
    public void write(int aByte)
    {
        verifyBufferSize(size + 1);
        buffer[size++] = (byte)aByte;
    }

    /**
     * Constructs a stream with buffer capacity size 5K
     */
    FastByteArrayOutputStream()
    {
        this(5 * 1024);
    }

    /**
     * Constructs a stream with the given initial size
     */
    FastByteArrayOutputStream(int initSize)
    {
        this.size = 0;
        this.buffer = new byte[initSize];
    }

    /**
     * Ensures that we have a large enough buffer for the given size.
     * @param newSize The new size
     */
    void verifyBufferSize(int newSize)
    {
        if (newSize > buffer.length)
        {
            byte[] old = buffer;
            buffer = new byte[Math.max(newSize, 2 * buffer.length)];
            System.arraycopy(old, 0, buffer, 0, old.length);
            old = null;
        }
    }

    /**
     *
     * @return The size
     */
    int getSize()
    {
        return size;
    }

    /**
     * Returns the byte array containing the written data. Note that this array will almost always be larger than the amount of data actually written.
     * @return array of bytes
     */
    byte[] getByteArray()
    {
        return buffer;
    }

    /** */
    void reset()
    {
        size = 0;
    }

    /**
     * Returns a ByteArrayInputStream for reading back the written data
     * @return InputStream
     */
    InputStream getInputStream()
    {
        return new FastByteArrayInputStream(buffer, size);
    }
}

/**
 * ByteArrayInputStream implementation that does not synchronize methods.
 * @author David Ainslie
 */
class FastByteArrayInputStream extends InputStream
{
    /** */
    private byte[] buffer;
    
    /** */
    private int count; // Number of bytes that we can read from the buffer
    
    /** */
    private int position; // Number of bytes that have been read from the buffer

    /**
     * @see java.io.InputStream#read()
     * @return Read how much
     */
	@Override
    public int read()
    {
        return (position < count) ? (buffer[position++] & 0xff) : -1;
    }

    /**
     * @see java.io.InputStream#read(byte[], int, int)
     * @param bytes array bytes
     * @param offSet Offset
     * @param length Length
     * @return Read hom much
     */
    @Override
    public int read(byte[] bytes, int offSet, int length)
    {
        if (position >= count)
        {
            return -1;
        }

        if ((position + length) > count)
        {
            length = count - position;
        }

        System.arraycopy(buffer, position, bytes, offSet, length);
        position += length;

        return length;
    }

    /**
     * @see java.io.InputStream#available()
     * @return How much available
     */
    @Override
    public int available()
    {
        return count - position;
    }

    /**
     * @see java.io.InputStream#skip(long)
     * @param n How many to skip
     * @return long
     */
    @Override
    public long skip(long n)
    {
        if ((position + n) > count)
        {
            n = count - position;
        }
        else if (n < 0)
        {
            return 0;
        }

        position += n;

        return n;
    }

	/**
	 *
	 * @param buffer Bytes array
	 * @param count Count
	 */
    FastByteArrayInputStream(byte[] buffer, int count)
    {
        this.buffer = buffer;
        this.count = count;
    }
}