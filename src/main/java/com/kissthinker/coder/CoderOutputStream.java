package com.kissthinker.coder;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kissthinker.configure.Configurable;
import com.kissthinker.configure.Configure;
import com.kissthinker.factory.Factory;
import com.kissthinker.id.IDGenerator;
import com.kissthinker.id.IntegerIDGenerator;
import com.kissthinker.proxy.Invocation;
import static com.kissthinker.collection.array.ArrayUtil.va;

/**
 * A {@link Coder} version of an {@link OutputStream} where objects are written (much like an {@link ObjectOutputStream} but as a standard stream of encoded bytes.
 *
 * Given objects (and {@link Invocation} objects) are encoded via a {@link Coder}. At the other end of the communication channel a {@link CoderInputStream} should be used.
 * Notes/Decisions regarding API (and implementation):
 * The method {@link #writeObject(Invocation)} returns an "id" that acts as a "message identifier", so that an outgoing "message" can be matched to one incoming.
 * The ID from an IDGenerator (configured/injected) can be thought of as analogous to a topic/queue name in JMS, or a RMI identifier
 * i.e we manage to have different forms of communication but all analogous.
 * Also note, that the coder streams were originally in project "io-kissthinker", and was moved to "core" to be alongside all other coder functionality - whether this is best I don't know.
 * @author David Ainslie
 *
 */
@Configurable
public class CoderOutputStream extends OutputStream
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(CoderOutputStream.class);

    /** */
    @Configure
    private static final Coder DEFAULT_CODER = new JsonCoder(); // TODO Get rid of this

    /** */
    private static final int BYTE_MASK = 0xff;

    /** */
    private static final int BYTE_SHIFT = 8;

    /** */
    private final OutputStream outputStream;

    /** */
    @Configure(id="idGenerator")
    private final IDGenerator<?> idGenerator = /*null*/ new IntegerIDGenerator(); // TODO Put back to null

    /**
     * @param outputStream OutputStream
     */
    public CoderOutputStream(OutputStream outputStream)
    {
        super();
        this.outputStream = outputStream;
    }

    /**
     *
     * @see java.io.OutputStream#write(int)
     */
    @Override
    public void write(int b) throws IOException
    {
        outputStream.write(b);
    }

    /**
     *
     * @param object Object
     * @throws IOException Issue encoding
     */
    public void writeObject(Object object) throws IOException
    {
        writeObject(idGenerator.next(), object);
    }

    /**
     *
     * @param invocation Invocation
     * @param <I> Object type
     * @return ID
     * @throws IOException Issue encoding
     */
    public <I> I writeObject(Invocation invocation) throws IOException
    {
        @SuppressWarnings("unchecked")
        I id = (I)idGenerator.next();

        writeObject(id, invocation);

        if (invocation.returns())
        {
            return id;
        }

        return null;
    }

    /**
     *
     * @param id ID
     * @param object Object
     * @param <I> Input type
     * @param <O> Output type
     * @throws IOException Issue encoding
     */
    public <I, O> void writeObject(I id, O object) throws IOException
    {
        // TODO locking on writing e.g. 3 writes together need to lock (same with read).

        Coder coder = encodeCoder(object);
        encodeObject(id, object, coder);

        LOGGER.trace("Written identifiable object (id = {}) {} with coder {}", va(id, object, coder));
    }

    /**
     *
     * @param <O> Object type
     * @param object Object
     * @return Coder
     * @throws IOException Issue encoding
     */
    private <O> Coder encodeCoder(O object) throws IOException
    {
        // Write out coder with 2 byte prefix depicting length.
        Coder coder = coder(object);

        byte[] coderBytes = coder.getClass().getName().getBytes();

        outputStream.write((coderBytes.length >> BYTE_SHIFT) & BYTE_MASK);
        outputStream.write(coderBytes.length & BYTE_MASK);
        outputStream.write(coderBytes);
        return coder;
    }

    /**
     * @param <O> Object type
     * @param <I> ID type
     * @param id ID
     * @param object Object
     * @param coder Coder
     * @throws IOException Issue encoding
     */
    private <O, I> void encodeObject(I id, O object, Coder coder) throws IOException
    {
        // Write out object, as an Identifiable, with 2 byte prefix depicting length.
        byte[] encodeBytes = coder.encode(new Identifiable<>(id, object));

        outputStream.write((encodeBytes.length >> BYTE_SHIFT) & BYTE_MASK);
        outputStream.write(encodeBytes.length & BYTE_MASK);
        outputStream.write(encodeBytes);
    }

    /**
     * Get {@link Coder} for given object, where if there is none available/found then use the {@link #DEFAULT_CODER}.
     * @param <O> Object type
     * @param object Object
     * @return Coder
     */
    private <O> Coder coder(O object)
    {
        if (object instanceof Invocation)
        {
            Invocation invocation = (Invocation)object;

            CoderClass coderClass = invocation.targetClass().getAnnotation(CoderClass.class); // TODO Check super classes.

            if (coderClass != null)
            {
                return Factory.create(coderClass.value()); // TODO Cache coders.
            }
        }

        // Check for specific (declared) Coder on given object.
        CoderClass coderClass = object.getClass().getAnnotation(CoderClass.class); // TODO Check super classes.

        if (coderClass != null)
        {
            return Factory.create(coderClass.value()); // TODO Cache coders.
        }

        return DEFAULT_CODER;
    }
}