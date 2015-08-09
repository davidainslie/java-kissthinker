package com.kissthinker.configure;

/**
 * @author David Ainslie
 *
 */
@Configurable
public class Bean1
{
    /** */
    @Configure
    private static final Bean2 BEAN_2 = null;

    /** */
    @Configure(otherwise="99")
    private static final Integer INTEGER = null;

    /** */
    @Configure(id="systemPropertyBeanByID")
    private final Bean2 bean2 = null;

    /** */
    @Configure
    private final Bean2 notToBeReplacedBean2 = new NotToBeReplacedBean2();

    /** */
    public Bean1()
    {
        super();
        System.out.println("bean2 in constructor is (should have been configured so not null) : " + bean2);
    }

    /** */
    public static Bean2 staticBean2()
    {
        return BEAN_2;
    }

    /** */
    public static Integer integer()
    {
        return INTEGER;
    }

    /** */
    public Bean2 bean2()
    {
        return bean2;
    }
    
    /** */
    public Bean2 notToBeReplacedBean2()
    {
        return notToBeReplacedBean2;
    }
}