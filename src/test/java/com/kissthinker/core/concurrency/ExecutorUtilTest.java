package com.kissthinker.core.concurrency;


import static org.junit.Assert.assertEquals;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import org.junit.Test;


/**
 * @author David Ainslie
 *
 */
public class ExecutorUtilTest
{

    /**
     *
     */
    public ExecutorUtilTest()
    {
        super();
    }


    /**
     * @throws InterruptedException
     *
     */
    @Test
    public void corePoolSizeCheck() throws InterruptedException
    {
        final CountDownLatch countDownLatch = new CountDownLatch(ExecutorUtil.coreMaximumPoolSize());
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(ExecutorUtil.coreMaximumPoolSize());

        for (int i = 0; i < ExecutorUtil.coreMaximumPoolSize(); i++)
        {
            new Thread(new Runnable()
            {
                /**
                 *
                 * @see java.lang.Runnable#run()
                 */
                @Override
                public void run()
                {
                    try
                    {
                        cyclicBarrier.await();

                        ExecutorUtil.execute(new Runnable()
                        {
                            /**
                             *
                             * @see java.lang.Runnable#run()
                             */
                            @Override
                            public void run()
                            {
                                countDownLatch.countDown();
                            }
                        });
                    }
                    catch (InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    catch (BrokenBarrierException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        countDownLatch.await();
        assertEquals(ExecutorUtil.corePoolSize(), ExecutorUtil.coreCurrentPoolSize());
    }


    /**
     * Do we really need both {@link #corePoolSizeCheck()} and {@link #corePoolSizeCheck2()} as they are pretty much the same?
     * @throws InterruptedException
     */
    @Test
    public void corePoolSizeCheck2() throws InterruptedException
    {
        final CountDownLatch countDownLatch = new CountDownLatch(ExecutorUtil.corePoolSize());

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(ExecutorUtil.corePoolSize(), new Runnable()
        {
            /**
             *
             * @see java.lang.Runnable#run()
             */
            @Override
            public void run()
            {
                assertEquals(ExecutorUtil.corePoolSize(), ExecutorUtil.coreCurrentPoolSize());
            }
        });

        for (int i = 0; i < ExecutorUtil.corePoolSize(); i++)
        {
            ExecutorUtil.execute(new Runnable()
            {
                /**
                 *
                 * @see java.lang.Runnable#run()
                 */
                @Override
                public void run()
                {
                    try
                    {
                        cyclicBarrier.await();
                        countDownLatch.countDown();
                    }
                    catch (InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    catch (BrokenBarrierException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }

        countDownLatch.await();
    }
}