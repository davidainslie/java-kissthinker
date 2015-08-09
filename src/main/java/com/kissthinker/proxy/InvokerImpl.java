package com.kissthinker.proxy;

/**
 * @author David Ainslie
 *
 */
@Deprecated
public class InvokerImpl implements Invoker
{
    /** */
    public InvokerImpl()
    {
        super();
    }

    /**
     *
     * @see com.kissthinker.proxy.Invoker#invoke(java.lang.Class, java.lang.String, java.lang.Object[])
     */
    @Override
    public <O> O invoke(Class<?> contractClass, String contractMethodName, Object... contractMethodArgs)
    {
        /*Object configuration = Configurations.get(id);

        if (configuration != null)
        {
            try
            {
                return (O)MethodUtils.invokeMethod(configuration, contractMethodName, contractMethodArgs);
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }*/

        return null;
    }
}