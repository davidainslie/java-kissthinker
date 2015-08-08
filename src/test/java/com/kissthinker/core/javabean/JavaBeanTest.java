package com.kissthinker.core.javabean;


import static com.kissthinker.core.collection.list.ListUtil.arrayList;
import static com.kissthinker.core.collection.map.MapUtil.hashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.kissthinker.core.collection.CollectionListener;
import com.kissthinker.core.collection.map.MapListener;


/**
 * @author David Ainslie
 * 
 */
public class JavaBeanTest
{
    /**
     * 
     */
    public JavaBeanTest()
    {
        super();
    }
    
    
    /**
     * 
     */
    @Before
    public void initialise()
    {
        PropertyChangeListenerDebug.reset();
        assertNull(PropertyChangeListenerDebug.propertyChangeListener());
    }
    
    
    /**
     * @throws InterruptedException 
     * 
     */
    @Test
    public void listenToJavaBeanByProperty() throws InterruptedException
    {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        
        Bean bean = new Bean();

        JavaBeanSupport.listen(bean, Bean.Properties.id, new PropertyListener<String>()
        {
            /**
             * 
             * @see com.kissthinker.core.javabean.PropertyListener#propertyChange(com.kissthinker.core.javabean.PropertyEvent)
             */
            @Override
            public void propertyChange(PropertyEvent<String> propertyEvent)
            {
                System.out.println(propertyEvent);
                assertEquals('B', propertyEvent.newValue().charAt(0));
                countDownLatch.countDown();
            }
        });

        bean.id("Boo");
        countDownLatch.await();
    }

    
    /**
     * This test is essentially duplicated {@link #listenToJavaBeanByProperty()} but also asserts correct garbage collection.
     * @throws InterruptedException
     */
    @Test
    public void listenToJavaBeanByPropertyCheckGC() throws InterruptedException
    {
        Thread thread = new Thread(new Runnable()
        {
            /**
             * 
             */
            @Override
            public void run()
            {
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                Bean bean = new Bean();

                JavaBeanSupport.listen(bean, Bean.Properties.id, new PropertyListener<String>()
                {
                    /**
                     * 
                     * @see com.kissthinker.core.javabean.PropertyListener#propertyChange(com.kissthinker.core.javabean.PropertyEvent)
                     */
                    @Override
                    public void propertyChange(PropertyEvent<String> propertyEvent)
                    {
                        System.out.println(propertyEvent);
                        assertEquals('B', propertyEvent.newValue().charAt(0));
                        countDownLatch.countDown();
                    }
                });

                assertNotNull(PropertyChangeListenerDebug.propertyChangeListener());
                
                bean.id("Boo");
                
                try
                {
                    countDownLatch.await();
                }
                catch (InterruptedException e)
                {
                    // Naughty; we are ignoring; but hey, it's just a test.
                    e.printStackTrace();
                }
            }            
        });
        
        thread.start();
        thread.join();
        // Note that after the "thread" is done, "bean" is out of scope and eligible for garbage collection.        
        
        int gcRequestCount = 0;
        
        while (PropertyChangeListenerDebug.propertyChangeListener() != null)
        {
            System.gc();
            gcRequestCount++;
        }
        
        System.out.println("Number of GC requests required = " + gcRequestCount);
    }
    
    
    /**
     * 
     * @throws InterruptedException
     */
    @Test
    public void listenToJavaBeanByPropertyName() throws InterruptedException
    {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        
        Bean bean = new Bean();

        JavaBeanSupport.listen(bean, "age", new PropertyListener<Integer>()
        {
            /**
             * 
             * @see com.kissthinker.core.javabean.PropertyListener#propertyChange(com.kissthinker.core.javabean.PropertyEvent)
             */
            @Override
            public void propertyChange(PropertyEvent<Integer> propertyEvent)
            {
                System.out.println(propertyEvent);
                assertEquals(101, propertyEvent.newValue().intValue());
                countDownLatch.countDown();
            }
        });

        bean.age(101);
        countDownLatch.await();
    }
    
    
    /**
     * 
     * @throws InterruptedException
     */
    @Test
    public void onAdd() throws InterruptedException
    {
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        Collection<Integer> integers = arrayList();
        
        JavaBeanSupport.listen(integers, new CollectionListener<Integer>()
        {
            /**
             * 
             * @param integer
             */
            @Override
            public void onAdd(Integer integer)
            {
                System.out.println("Got: " + integer);
                countDownLatch.countDown();                
            }

            
            /**
             * 
             * @param integer
             */
            @Override
            public void onRemove(Integer integer)
            {
                // TODO Auto-generated method stub
                
            }
        });
        
        integers.add(44);
        integers.add(77);
        
        boolean complete = countDownLatch.await(5, TimeUnit.SECONDS);
        
        if (!complete)
        {
            fail();
        }
    }
    
    
    /**
     * 
     * @throws InterruptedException
     */
    @Test
    public void onPut() throws InterruptedException
    {
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        Map<Integer, String> stringsByInteger = hashMap();
        
        JavaBeanSupport.listen(stringsByInteger, new MapListener<Integer, String>()
        {
            /**
             * 
             * @param key
             * @param value
             */
            @Override
            public void onPut(Integer key, String value)
            {
                System.out.printf("Put key:%s, value:%s%n", key, value);
                countDownLatch.countDown();
            }

            
            /**
             * 
             * @param key
             * @param value
             */
            @Override
            public void onRemove(Integer key, String value)
            {
                // TODO Auto-generated method stub
                
            }
        });
        
        stringsByInteger.put(44, "Scooby");
        stringsByInteger.put(77, "Doo");
        
        boolean complete = countDownLatch.await(5, TimeUnit.SECONDS);
        
        if (!complete)
        {
            fail();
        }
    }
    
    
    /**
     * 
     * @throws InterruptedException
     */
    @Test
    public void onPutWithNullKey() throws InterruptedException
    {
        final CountDownLatch countDownLatch = new CountDownLatch(3);
        Map<Integer, String> stringsByInteger = hashMap();
        
        MapListener<Integer, String> mapListener = new MapListener<Integer, String>()
        {
            /**
             * 
             * @param key
             * @param value
             */
            @Override
            public void onPut(Integer key, String value)
            {
                System.out.printf("Put key:%s, value:%s%n", key, value);
                countDownLatch.countDown();
            }

            
            /**
             * 
             * @param key
             * @param value
             */
            @Override
            public void onRemove(Integer key, String value)
            {
                // TODO Auto-generated method stub
                
            }
        };
        
        mapListener.onPut(44, "Scooby");
        mapListener.onPut(null, "Scooby");
        
        JavaBeanSupport.listen(stringsByInteger, mapListener);
        
        stringsByInteger.put(null, "Scooby");
        
        countDownLatch.await();
    }    
}