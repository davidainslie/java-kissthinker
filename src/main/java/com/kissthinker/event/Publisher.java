package com.kissthinker.event;

import java.util.Set;
import com.kissthinker.function.Function;
import static com.kissthinker.collection.set.SetUtil.hashSet;

/**
 *
 * @author David Ainslie
 * S for source to suscribe to and E for the published event can of course be the same type if so desired.
 * @param <S> Source type
 * @param <E> Event type
 */
public abstract class Publisher<S, E>
{
    /** */
    private final Set<Subscription<S, E>> subscriptions = hashSet(); // TODO Need a weak hash set

    /** */
    public Publisher()
    {
        super();
    }

    /**
     *
     * @param subject Subject
     * @param callback Callback
     */
    public void subscribe(S subject, Function<?> callback)
    {
        subscribe(subject, callback, null);
    }

    /**
     *
     * @param subject Subject
     * @param callback Callback
     * @param criteria Criteria
     */
    // TODO public void subscribe(S subject, Function<?> callback, Function<Boolean>... criteria)
    public void subscribe(S subject, Function<?> callback, Function<Boolean> criteria)
    {
        subscriptions.add(createSubscription(subject, callback, criteria));
    }

    /**
     *
     * @param event Event
     */
    public void publish(E event)
    {
        // TODO On background thread, take a copy to iterate over.
        for (Subscription<S, E> subscription : subscriptions)
        {
            if (subscription.criteria() != null)
            {
                if (!subscription.criteria().apply(event))
                {
                    continue;
                }
            }

            subscription.callback().apply(event);
        }
    }

    /**
     * Contract.
     * @param subject Subject
     * @param callback Callback
     * @param criteria Criteria
     * @return Subscription
     */
    protected abstract Subscription<S, E> createSubscription(S subject, Function<?> callback, Function<Boolean> criteria);
}