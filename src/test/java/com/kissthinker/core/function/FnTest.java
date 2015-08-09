package com.kissthinker.core.function;

import java.util.*;
import org.junit.Before;
import org.junit.Test;
import com.kissthinker.core.collection.CollectionUtil;
import com.kissthinker.core.collection.CollectionUtilTest;
import com.kissthinker.core.object.Type;
import static com.kissthinker.core.collection.list.ListUtil.arrayList;
import static com.kissthinker.core.function.Fn.*;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * To test creating and executing functions.
 * <br/>
 * Note that originally this class was named FunctionUtilTest as it was testing FunctionUtil, but that class has been renamed Fn.
 * @author David Ainslie
 *
 */
public class FnTest
{
    /** */
    private static String FUNCTION_RESULT = "Done";

    /** */
    private static int functionApplyCount;

    /** */
    private List<Bean> beans;

    /** */
    public FnTest()
    {
        super();
    }

    /** */
    @Before
    public void initialise()
    {
        functionApplyCount = 0;
        
        beans = arrayList(new Bean("Dad", 42),
                          new Bean("Mum", 40),
                          new Bean("Son 2nd", 1),
                          new Bean("Son 1st", 7),
                          new Bean("Grandad", 99));
    }

    /** */
    @Test
    public void applyFunction()
    {
        Function<String> function = function(from(AFunction.class).doSomething());

        System.out.printf("Applying function: %s%n", function);
        assertThat(0).isEqualTo(functionApplyCount);
        String result = function.apply();
        assertThat(FUNCTION_RESULT).isEqualTo(result);
        assertThat(1).isEqualTo(functionApplyCount);
    }

    /** */
    @Test
    public void applyFunctionInstantiatedInline()
    {
        Function<String> function = function(from(new Function.Callback<String, Void>()
        {
            /**
             *
             * @see com.kissthinker.core.function.FnTest.Callback#callback(java.lang.Object)
             */
            @Override
            public String onCallback(Void nill)
            {
                functionApplyCount++;
                return FUNCTION_RESULT;
            }
        }));

        System.out.printf("Applying function: %s%n", function);
        assertThat(0).isEqualTo(functionApplyCount);
        String result = function.apply(Type.VOID);
        assertThat(FUNCTION_RESULT).isEqualTo(result);
        assertThat(1).isEqualTo(functionApplyCount);
    }

    /** */
    @Test
    public void instantiateAndApplyFunction()
    {
        Function<Class<?>[]> function = ClassHierarchyFunction.instance();

        System.out.printf("Applying function: %s%n", function);
        assertThat(0).isEqualTo(functionApplyCount);
        Class<?>[] result = function.apply(this);
        assertThat(result.length > 0).isTrue();
        System.out.println(Arrays.toString(result));
        assertThat(1).isEqualTo(functionApplyCount);
    }

    /** */
    @Test
    public void applyMeAsFunction()
    {
        Function<Boolean> function = function(from(this).applyMe(null));

        System.out.printf("Applying function: %s%n", function);
        assertThat(0).isEqualTo(functionApplyCount);
        Boolean result = function.apply("whatever");
        assertThat(result).isTrue();
        assertThat(1).isEqualTo(functionApplyCount);


        Function<Void> function2 = function(from(this).applyMeAgain());

        System.out.printf("Applying function: %s%n", function2);
        assertThat(1).isEqualTo(functionApplyCount);
        Void result2 = function2.apply();
        assertThat(result2).isNull();
        assertThat(2).isEqualTo(functionApplyCount);

        Function<Void> function3 = function(from(this).applyMeAgainReturningActualVoid());

        System.out.printf("Applying function: %s%n", function3);
        assertThat(2).isEqualTo(functionApplyCount);
        Void result3 = function3.apply();
        assertThat(result3 instanceof Void).isTrue();
        assertThat(3).isEqualTo(functionApplyCount);
    }

    /**
     * Note that a "function" method must be public and return "something".<br/>
     * This can be expected of a "function" as a function should take an input(s) and give back some output.<br/>
     * And public, because a function can be passed to around to any class in any package, so accessibility is required.
     * @param whatever
     * @return Boolean
     */
    public Boolean applyMe(String whatever)
    {
        functionApplyCount++;
        return true;
    }

    /**
     * Note that a "function" method must be public and return "something".<br/>
     * This can be expected of a "function" as a function should take an input(s) and give back some output.<br/>
     * And public, because a function can be passed to around to any class in any package, so accessibility is required.
     * @return
     */
    public Void applyMeAgain()
    {
        functionApplyCount++;
        return null;
    }

    /**
     * Note that a "function" method must be public and return "something".<br/>
     * This can be expected of a "function" as a function should take an input(s) and give back some output.<br/>
     * And public, because a function can be passed to around to any class in any package, so accessibility is required.
     * @return
     */
    public Void applyMeAgainReturningActualVoid()
    {
        functionApplyCount++;
        return Type.VOID;
    }

    /**
     * Referring to test {@link #executeFunction()} why would we not just implement the {@link Function} interface directly?
     * We can, as this test shows. However, we lose the power of filter/map/reduce as exposed by {@link CollectionUtil} shown in {@link CollectionUtilTest}
     */
    @Test
    public void applyFunctionViaDirectImplementation()
    {
        Function<String> function = new Function<String>()
        {
            /**
             *
             * @see com.kissthinker.core.function.Function#apply(java.lang.Object[])
             */
            @Override
            public String apply(Object... args)
            {
                functionApplyCount++;
                return FUNCTION_RESULT;
            }
        };

        System.out.printf("Applying function: %s%n", function);
        assertThat(0).isEqualTo(functionApplyCount);
        String result = function.apply();
        assertThat(FUNCTION_RESULT).isEqualTo(result);
        assertThat(1).isEqualTo(functionApplyCount);
    }

    /**
     *
     */
    @Test
    public void applyFunctionWithPolymorphismNotDoubleDispatchAsWithVisitor()
    {
        // If the following were given Bean or generated from some type of factory,
        // the following functions (on the surface) would only be dealing with Bean, and yet the methods that are called, are a consequence of how the functions work.
        Bean bean = new Bean();
        Bean bean2 = new Bean2();

        Function<String> function = function(from(this).onBean(bean));
        assertThat("Just Bean").isEqualTo(function.apply(bean));
        assertThat("Just Bean").isEqualTo(function.apply(bean2));

        function = function(from(this).onBean(bean2));
        assertThat("Ah! Bean2").isEqualTo(function.apply(bean2));

        try
        {
            function.apply(bean);
            fail("Should not have got here");
        }
        catch (Exception e)
        {
            System.out.println("Correctly got " + e.getMessage());
        }
    }

    /**
     *
     * @param bean
     * @return
     */
    public String onBean(Bean bean)
    {
        return "Just Bean";
    }

    /**
     *
     * @param bean2
     * @return
     */
    public String onBean(Bean2 bean2)
    {
        return "Ah! Bean2";
    }

    /**
     * Notice 0 passed to call to "timesTen".
     * As the function is not actually executed at that point, it is just a placeholder.
     * In other tests we show that instead of using a primitive (such as int) as a param
     */
    @Test
    public void forEachInteger()
    {
        Function<Integer> function = function(from(Maths.class).timesTen(0));

        List<Integer> numbers = arrayList(1, 2, 3, 4, 5);
        List<Integer> newNumbers = forEach(numbers, function);
        assertThat(arrayList(10, 20, 30, 40, 50)).isEqualTo(newNumbers);
    }

    /**
     * Note that this test does not use the following syntax:
     * <pre>
     * filter(Arrays.asList(42, 40, 1, 7, 99), function(from(CanDrink.class).check(null)));
     * where the following import would have been used:
     * import static com.kissthinker.core.function.Fn.function;
     * The reason for not being able to utilise the above, is down to Generics - we have to stipulate the function return type.
     * And why do we pass in that "null" to the call on method "check"?
     * We are not really calling the method at that point, we generating a function but need the "placeholder".
     * This is a tad undesired, but a consequence of fitting something (in this case functional programming) into Java as a workaround for that something missing.
     * </pre>
     */
    @Test
    public void filterCollection()
    {
        Collection<Integer> filteredCollection = filter(Arrays.asList(42, 40, 1, 7, 99),
                                                        Fn.<Boolean>function(from(CanDrink.class).check(null)));

        System.out.println(CollectionUtil.toString(filteredCollection));
        assertThat(3).isEqualTo(filteredCollection.size());
    }

    /** */
    @Test
    public void filterList()
    {
        Function<Boolean> function = function(from(CanDrink.class).check(null));
        List<Integer> filteredList = filter(Arrays.asList(42, 40, 1, 7, 99), function);

        assertThat(3).isEqualTo(filteredList.size());
    }

    /**
     * Note that this test does not use the following syntax:
     * <pre>
     * functions.add(function(from(CanDrink.class).check(null)));
     * where the following import would have been used:
     * import static com.kissthinker.core.function.Fn.function;
     * The reason for not being able to utilise the above, is down to Generics - we have to stipulate the function return type.
     * NOTE that instead of a list, we could have had:
     * List<Integer> filteredList = filter(filter(Arrays.asList(42, 40, 1, 7, 99),
     *                                            Fn.<Boolean>function(from(CanDrink.class).check(null))),
     *                                     Fn.<Boolean>function(from(IsSenior.class).check(null)));
     * </pre>
     */
    @Test
    public void filterListTwice()
    {
        List<Function<Boolean>> functions = arrayList();
        functions.add(Fn.<Boolean>function(from(CanDrink.class).check(null)));
        functions.add(Fn.<Boolean>function(from(IsSenior.class).check(null)));

        List<Integer> filteredList = filter(Arrays.asList(42, 40, 1, 7, 99), functions);

        assertThat(1).isEqualTo(filteredList.size());
    }

    /**
     * This test shows the power/convenience of using {@link Fn} instead of directly implementing {@link Function} as mentioned in {@link FnTest}.
     * A better example is the test for map {@link mapBeansToStrings}
     */
    @Test
    public void filterBeans()
    {
        Function<Boolean> function = function(from(BeanCanDrink.class).check(null));

        List<Bean> filteredBeans = filter(beans, function);

        System.out.println(CollectionUtil.toString(filteredBeans));
        assertThat(3).isEqualTo(filteredBeans.size());
    }

    /**
     *
     */
    @Test
    public void filterSet()
    {
        Set<Integer> set = new HashSet<>();
        set.add(42);
        set.add(40);
        set.add(1);
        set.add(7);
        set.add(99);

        Function<Boolean> function = function(from(CanDrink.class).check(null));
        Set<Integer> filteredSet = filter(set, function);

        assertThat(3).isEqualTo(filteredSet.size());
    }

    /**
     *
     */
    @Test
    public void mapBeansToStrings()
    {
        int listLastIndex = beans.size() - 1;
        
        Function<String> function = function(from(Bean.class).name());

        List<String> beanNames = map(beans, function);

        assertThat(beans.size()).isEqualTo(beanNames.size());
        
        assertThat(new Bean("Grandad", 99)).isEqualTo(beans.get(listLastIndex));
        assertThat("Grandad").isEqualTo(beanNames.get(listLastIndex));
    }

    /**
     * 
     */
    @Test
    public void sumListOfIntegersByFoldLeft()
    {
        List<Integer> integers = arrayList(2, 5, 18);
        
        Function<Integer> add = function(from(this).add(null, null));
        
        Integer sum = foldLeft(integers, 0, add);
        assertThat(25).isEqualTo(sum);
    }

    /**
     * Can be used as a function, and it is used in that way by {@link #sumListOfIntegersByFoldLeft()}
     * @param i1
     * @param i2
     * @return Integer the result of adding i1 and i2
     */
    public Integer add(Integer i1, Integer i2)
    {
        return i1 + i2;
    }

    /**
     *
     * @author David Ainslie
     *
     */
    public static class AFunction
    {
        /** */
        public String doSomething()
        {
            functionApplyCount++;
            return FUNCTION_RESULT;
        }
    }

    /**
     * Function that is thread safe (has no state) and performant as function is only created once (and essentially cached).
     * <br/>
     * @author David Ainslie
     *
     */
    public static class ClassHierarchyFunction
    {
        /** */
        private static final Function<Class<?>[]> INSTANCE = function(from(ClassHierarchyFunction.class).classHierarchy(null));

        /**
         * Use this method to gain access to the function.
         * @return
         */
        public static Function<Class<?>[]> instance()
        {
            return INSTANCE;
        }

        /**
         * This is the method that is actually wrapped up in a function.
         * @param object
         * @return
         */
        public Class<?>[] classHierarchy(Object object)
        {
            functionApplyCount++;

            List<Class<?>> classes = arrayList();

            for (Class<?> class_ = object.getClass(); class_ != null; class_ = class_.getSuperclass())
            {
                classes.add(class_);
            }

            Collections.reverse(classes);
            return classes.toArray(new Class<?>[0]);
        }
    }

    /**
     *
     * @author David Ainslie
     *
     */
    public static class Maths
    {
        /**
         *
         * @param number
         * @return given number multiplied by 10
         */
        public int timesTen(int number)
        {
            return number * 10;
        }
    }

    /**
     *
     * @author David Ainslie
     *
     */
    public static class CanDrink
    {
        /**
         *
         * @param age
         * @return
         */
        public boolean check(Integer age)
        {
            return age >= 18;
        }
    }

    /**
     *
     * @author David Ainslie
     *
     */
    public static class IsSenior
    {
        /**
         *
         * @param age
         * @return
         */
        public boolean check(Integer age)
        {
            return age >= 65;
        }
    }

    /**
     *
     * @author David Ainslie
     *
     */
    public static class Bean
    {
        /** */
        private /*final*/ String name;

        /** */
        private /*final*/ int age;

        /**
         * TODO Currently need no-arg constructor when using this class as a function. Note the "final" commented out in fields.
         */
        public Bean()
        {
            super();
        }

        /**
         * @param name
         * @param age
         */
        public Bean(String name, int age)
        {
            super();
            this.name = name;
            this.age = age;
        }

        /**
         * Getter.
         * @return the name
         */
        public String name()
        {
            return name;
        }

        /**
         * Getter.
         * @return the age
         */
        public int age()
        {
            return age;
        }

        /**
         * Autogenerated
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + age;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }

        /**
         * Autogenerated
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Bean other = (Bean)obj;
            if (age != other.age)
                return false;
            if (name == null)
            {
                if (other.name != null)
                    return false;
            }
            else if (!name.equals(other.name))
                return false;
            return true;
        }
    }

    /**
     *
     * @author David Ainslie
     *
     */
    public static class Bean2 extends Bean
    {
    }

    /**
     *
     * @author David Ainslie
     *
     */
    public static class BeanCanDrink
    {
        /**
         *
         * @param bean
         * @return
         */
        public boolean check(Bean bean)
        {
            return bean.age() >= 18;
        }
    }
}