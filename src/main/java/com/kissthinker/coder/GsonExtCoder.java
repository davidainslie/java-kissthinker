package com.kissthinker.coder;

import com.gson4javaext.core.GSON4JavaExt;

/**
 * Use enhanced version of {@link GSON4JavaExt} to handle enums.
 * <br/>
 * TODO {@link GSON4JavaExt} was enhanced because it did not (originally) handle enums (even though the website claimed this).<br/>
 * Well, also it does not handle collections (again the website has claimed this).
 * @author David Ainslie
 *
 */
public class GsonExtCoder implements Coder
{
    /** */
    public GsonExtCoder()
    {
        super();
    }

    /**
     * @see com.kissthinker.coder.Coder#decode(byte[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public <O> O decode(byte[] bytes)
    {
        return (O)GSON4JavaExt.fromJson(new String(bytes));
    }

    /**
     * @see com.kissthinker.coder.Coder#encode(java.lang.Object)
     */
    @Override
    public <O> byte[] encode(O object)
    {
        return GSON4JavaExt.toJsonString(object).getBytes();
    }
}