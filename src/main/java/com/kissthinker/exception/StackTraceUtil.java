package com.kissthinker.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import com.kissthinker.object.Singleton;

/**
 * @author David Ainslie
 *
 */
@Singleton
public final class StackTraceUtil
{
    /**
     * Get String representation of stack trace from given {@link Throwable}
     * @param t Throwable
     * @return String
     */
    public static String toString(Throwable t)
    {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        t.printStackTrace(printWriter);

        return writer.toString();
    }

    /**
     * Utility.
     */
    private StackTraceUtil()
    {
        super();
    }
}