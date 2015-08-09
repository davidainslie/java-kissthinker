package com.kissthinker.core.coder;

import java.io.Serializable;
import java.util.Currency;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import static com.kissthinker.core.collection.set.SetUtil.hashSet;

/**
 * @author David Ainslie
 *
 */
public class Bean implements Serializable
{
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    private String id = "Scooby";

    /** */
    private int age = 42;

    /** */
    private Set<Currency> majorCurrencies = hashSet(Currency.getInstance("GBP"), Currency.getInstance("USD")); // etc.

    /** */
    private Set<Currency> allCurrencies = allCurrencies();

    /** */
    private String largeString = largeString();

    /**
     *
     */
    public Bean()
    {
        super();
    }

    /**
     *
     * @return
     */
    private static Set<Currency> allCurrencies()
    {
        Set<Currency> currencies = hashSet();

        for (Locale locale : Locale.getAvailableLocales())
        {
            try
            {
                currencies.add(Currency.getInstance(locale));
            }
            catch (Exception e)
            {
                // Ignore - naughty. An example of what is ignored (error that I originally logged):
                // Igoring curency issue for locale en
                // Igoring curency issue for locale mk
            }
        }

        return currencies;
    }

    /**
     *
     * @return
     */
    private static String largeString()
    {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 1000; i++)
        {
            stringBuilder.append(i).append("=").append(UUID.randomUUID()).append(",");
        }

        return stringBuilder.toString();
    }
}