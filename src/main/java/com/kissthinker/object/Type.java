package com.kissthinker.object;

import java.lang.reflect.Constructor;

/**
 * @author David Ainslie
 *
 */
public interface Type
{
    /** A (singleton) instance of Void.class.
     *  e.g instead of returning null (which is fine if that is your preference) for a method of return type Void, return Type.VOID.
     *  Why? Helps to keep code self documenting. However, this is only a preference - I've mixed (throughout KISS code) nulls and Voids for illustration purposes. */
    Void VOID = TypesHelper.Void();

    /**
     *
     * @author David Ainslie
     *
     */
    static class TypesHelper
    {
        /** */
        private static Void Void()
        {
            try
            {
                Constructor<?>[] custructors = Void.class.getDeclaredConstructors();
                custructors[0].setAccessible(true);
                return (Void)custructors[0].newInstance();
            }
            catch (Exception e)
            {
                throw new Error(e);
            }
        }
    }
}