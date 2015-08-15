package com.kissthinker.coder;

import java.io.Serializable;
import java.util.*;
import static com.kissthinker.collection.set.SetUtil.hashSet;

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

    /** */
    public Bean()
    {
        super();
    }

    /** */
    public String getId() {
        return id;
    }

    /** */
    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    /** */
    public void setAge(int age) {
        this.age = age;
    }

    /** */
    public Set<Currency> getMajorCurrencies() {
        return majorCurrencies;
    }

    /** */
    public void setMajorCurrencies(Set<Currency> majorCurrencies) {
        this.majorCurrencies = majorCurrencies;
    }

    /** */
    public Set<Currency> getAllCurrencies() {
        return allCurrencies;
    }

    /** */
    public void setAllCurrencies(Set<Currency> allCurrencies) {
        this.allCurrencies = allCurrencies;
    }

    /** */
    public String getLargeString() {
        return largeString;
    }

    /** */
    public void setLargeString(String largeString) {
        this.largeString = largeString;
    }

    /** */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bean bean = (Bean) o;
        return Objects.equals(age, bean.age) &&
                Objects.equals(id, bean.id) &&
                Objects.equals(majorCurrencies, bean.majorCurrencies) &&
                Objects.equals(allCurrencies, bean.allCurrencies) &&
                Objects.equals(largeString, bean.largeString);
    }

    /** */
    @Override
    public int hashCode() {
        return Objects.hash(id, age, majorCurrencies, allCurrencies, largeString);
    }

    /** */
    @Override
    public String toString() {
        return "Bean{" +
                "id='" + id + '\'' +
                ", age=" + age +
                ", majorCurrencies=" + majorCurrencies +
                ", allCurrencies=" + allCurrencies +
                ", largeString='" + largeString + '\'' +
                '}';
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