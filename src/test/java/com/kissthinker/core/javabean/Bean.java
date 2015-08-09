package com.kissthinker.core.javabean;

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
public class Bean implements JavaBean.Enumerated<Bean.Properties>, Serializable
{
    /** */
    public enum Properties {id, /*age <--- as age is commented, a listener set up must refer by string i.e. "age" */}
    
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    @Property
    private String id = "Scooby";

    /** */
    @Property
    private int age = 42;

    /** */
    private final Set<Currency> majorCurrencies = hashSet(Currency.getInstance("GBP"), Currency.getInstance("USD")); // etc.

    /** */
    private final Set<Currency> allCurrencies = allCurrencies();

    /** */
    private final String largeString = largeString();

    /** */
    public Bean()
    {
        super();
    }

    /**
     * Getter
     * @return the id
     */
    public String id()
    {
        return id;
    }

    /**
     * Setter
     * @param id the id to set
     * @return Bean
     */
    public Bean id(String id)
    {
        this.id = id;
        return this;
    }

    /**
     * Getter
     * @return the age
     */
    public int age()
    {
        return age;
    }

    /**
     * Setter
     * @param age the age to set
     * @return Bean
     */
    public Bean age(int age)
    {
        this.age = age;
        return this;
    }

    /** */
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

    /** */
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