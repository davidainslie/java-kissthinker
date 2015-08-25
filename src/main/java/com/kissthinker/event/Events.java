package com.kissthinker.event;

import java.util.List;
import java.util.Map;
import java.util.Set;
import com.kissthinker.function.Function;
import static com.kissthinker.collection.list.ListUtil.arrayList;
import static com.kissthinker.collection.map.MapUtil.hashMap;
import static com.kissthinker.collection.map.MapUtil.weakHashMap;
import static com.kissthinker.collection.set.SetUtil.hashSet;

/**
 * @author David Ainslie
 *
 */
public final class Events
{
    /** */
    private static final Map<Class<?>, Set<Object>> PUBLISHERS = hashMap();

    /** */
    private static final Map<Class<?>, List<Subscription<?>>> SUBSCRIPTIONS = weakHashMap();

    /* TODO IDEA
    @Configure
    private Set<Publisher> publishers;*/

    /**
     *
     * @param <E> Event type
     * @param <L> Listener type
     * @param event Event
     * @param listener Listener
     */
    public static <E, L> void listen(E event, L listener)
    {
    }

    /**
     *
     * @param <E> Event type
     * @param <S> Source type
     * @param <L> Listener type
     * @param event Event
     * @param source Source
     * @param listener Listener
     */
    public static <E, S, L> void listen(E event, S source, L listener)
    {
    }

    /**
     *
     * @param <E> Event type
     * @param event Event
     * @param function Function
     */
    public static <E> void listen(E event, Function<?> function)
    {
    }

    /**
     *
     * @param <E> Event type
     * @param <S> Source type
     * @param event Event
     * @param source Source
     * @param function Function
     */
    public static <E, S> void listen(E event, S source, Function<?> function)
    {
    }

    /**
     *
     * @param <E> Event type
     * @param eventClass Event class
     * @param publisher Publisher
     */
    public static <E> void register(Class<E> eventClass, Object publisher)
    {
        Set<Object> publishers = PUBLISHERS.get(eventClass);

        if (publishers == null)
        {
            publishers = hashSet();
            PUBLISHERS.put(eventClass, publishers);
        }

        publishers.add(publisher);
    }

    /**
     *
     * @param <E> Event type
     * @param eventClass Event class
     * @param function Function
     */
    public static <E> void subscribe(Class<E> eventClass, Function<Object> function)
    {
        subscribe(eventClass, null, function);
    }

    /**
     *
     * @param <E> Event type
     * @param eventClass Event class
     * @param criteria Criteria
     * @param function Function
     */
    public static <E> void subscribe(Class<E> eventClass, Criteria<E> criteria, Function<Object> function)
    {
        List<Subscription<?>> subscriptions = SUBSCRIPTIONS.get(eventClass);

        if (subscriptions == null)
        {
            subscriptions = arrayList();
            SUBSCRIPTIONS.put(eventClass, subscriptions);
        }

        subscriptions.add(new Subscription<E>(function, criteria));
    }

    /**
     *
     * @param <E> Event type
     * @param event Event
     */
    public static <E> void publish(E event)
    {
        //System.out.println(w);
        List<Subscription<?>> subscriptions = SUBSCRIPTIONS.get(event.getClass());

        // TODO On background thread, take a copy to iterate over.
        for (Subscription<?> s : subscriptions)
        {
            Subscription<E> subscription = (Subscription<E>)s;

            if (subscription.criteria() != null)
            {
                if (!subscription.criteria().check(event))
                {
                    continue;
                }
            }

            subscription.function().apply(event);
        }
    }

    /**
     *
     * @param <E> Event type
     * @param event Event
     */
    public static <E> void fire(E event)
    {
        // TODO Auto-generated method stub
    }

    /**
     *
     * @param <E> Event type
     * @param <S> Source type
     * @param event Event
     * @param source Source
     */
    public static <E, S> void fire(E event, S source)
    {
        // TODO Auto-generated method stub
    }

    /**
     *
     */
    private Events()
    {
        super();
    }

    /**
     *
     * @author David Ainslie
     *
     * @param <E>
     */
    private static class Subscription<E>
    {
        /** */
        private final Function<Object> function;

        /** */
        private Criteria<E> criteria;

        /**
         *
         * @param function Function
         */
        private Subscription(Function<Object> function)
        {
            this(function, null);
        }

        /**
         * @param function Function
         * @param criteria Criteria
         */
        private Subscription(Function<Object> function, Criteria<E> criteria)
        {
            super();
            this.function = function;
            this.criteria = criteria;
        }

        /**
         * @return the function
         */
        private Function<Object> function()
        {
            return function;
        }

        /**
         * @return the criteria
         */
        private Criteria<E> criteria()
        {
            return criteria;
        }
    }
}