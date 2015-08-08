package com.kissthinker.core.reflect;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;

import org.junit.Test;

/*import com.ddtechnology.property.PropertyEvent;
import com.ddtechnology.property.PropertyListener;
import com.ddtechnology.property.PropertySupport;
import com.ddtechnology.property.User;
import com.ddtechnology.property.User.Gender;*/


/**
 * @author David Ainslie
 */
public class ObjectUtilTest
{
    /**
     * 
     */
    public ObjectUtilTest()
    {
        super();
        // TODO Auto-generated constructor stub
    }


    /**
     *
     */
    @Test
    public void packageName()
    {
        assertEquals("com.kissthinker.core.reflect", ObjectUtil.packageName(this));
        assertEquals("com.kissthinker.core.reflect", ObjectUtil.packageName(getClass()));
    }


    /**
     *
     * @throws Exception
     */
    @Test
    public void primitive() throws Exception
    {
        Integer integer = 0;

        assertTrue(integer.getClass().isInstance(0));
        assertNotNull(integer.getClass().cast(0));

        Constructor<?> constructor = integer.getClass().getConstructor(String.class);
        assertNotNull(constructor.newInstance(integer.toString()));

        Boolean bool = true;

        constructor = bool.getClass().getConstructor(String.class);
        assertNotNull(constructor.newInstance(bool.toString()));
    }


    /**
     *
     */
    /*@Test
    public void hasProperty()
    {
        Pojo1 pojo1Contains = new Pojo1();
        Pojo1 pojo1NotContains = new Pojo1();

        Pojo pojo = new Pojo(pojo1Contains);

        assertTrue(ObjectUtil.hasProperty(pojo, pojo1Contains));
        assertFalse(ObjectUtil.hasProperty(pojo, pojo1NotContains));
    }*/


    /**
     *
     */
    /*@Test
    public void deepCopy()
    {
        User user = new User("Scooby Doo", Gender.male, false);
        User userCopy = ObjectUtil.deepCopy(user);
        assertEquals("Scooby Doo", userCopy.getName());
    }*/


    /**
     *
     */
    /*@Test
    public void deepCopyViaJBoss()
    {
        User user = new User("Scooby Doo", Gender.male, false);
        User userCopy = ObjectUtil.deepCopyViaJBoss(user);
        assertEquals("Scooby Doo", userCopy.getName());
    }*/


    /**
     * @throws InterruptedException
     *
     */
    /*@Test
    public void deepCopyAndFireProperyChangeEvent() throws InterruptedException
    {
        final CountDownLatch countDownLatch = new CountDownLatch(2);

        List<Address> homes = new ArrayList<>();
        Address home = new Address("24 Home Street, UK", "0208 666 8888");
        homes.add(home);
        homes.add(new Address("109 Abroad Street, France", "000 999 00 11"));

        Woman woman = new Woman(home);
        Man man = new Man(woman, homes);

        Man newMan = ObjectUtil.deepCopy(man);

        PropertySupport.listen(man.getHome(), "telephone", new PropertyListener<String>()
        {
            @Override
            public void propertyChange(PropertyEvent<String> propertyEvent)
            {
                try
                {
                    TimeUnit.SECONDS.sleep(3);
                    countDownLatch.countDown();
                }
                catch (InterruptedException e)
                {
                }
            }
        });

        PropertySupport.listen(newMan.getHome(), "telephone", new PropertyListener<String>()
        {
            @Override
            public void propertyChange(PropertyEvent<String> propertyEvent)
            {
                fail();
                countDownLatch.countDown();
            }
        });

        PropertySupport.listen(woman.getHome(), "telephone", new PropertyListener<String>()
        {
            @Override
            public void propertyChange(PropertyEvent<String> propertyEvent)
            {
                countDownLatch.countDown();
            }
        });

        home.setTelephone("changed telephone number for home");
        countDownLatch.await();

        assertEquals("changed telephone number for home", man.getHome().getTelephone());
        assertEquals("changed telephone number for home", woman.getHome().getTelephone());
        assertEquals("0208 666 8888", newMan.getHome().getTelephone());
    }*/
}