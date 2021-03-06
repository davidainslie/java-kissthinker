package com.kissthinker.system;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kissthinker.collection.CollectionUtil;
import com.kissthinker.collection.array.ArrayUtil;

/**
 * Available environments.
 *
 * Environment can be set with system property "environment".
 * @author David Ainslie
 *
 */
public enum Environment
{
    /** A convenience setting that EXCLUDES {@link #TEST} and {@link #LOCAL} i.e. integration environments */
    INTEGRATION("Integration"),

    /** Standard integration environment */
    LIVE("Live (Production)"),

    /** Standard integration environment */
    STAGING("Staging (Pre Live)"),

    /** Standard integration environment */
    UAT("User Acceptance Testing (UAT)"),

    /** Standard integration environment */
    DEV("Development"),

    /** Standalone (local calls) i.e no remoting and no internet required. */
    LOCAL("Local"),

    /** Test environment for unit testing and mocking that involves standalone (local calls) i.e no remoting and no internet required. */
    TEST("Test"),

    /** Convenience covering any (all) environments. */
    ANY("Any");

    /** Runtime environment which defaults to {@link #TEST} as any other runtime environment should (must) be given. */
    public static final Environment RUNTIME;

    /** */
    private static final List<Environment> INTEGRATION_ENVIRONMENTS;

    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(Environment.class);

    /** */
    private final String displayable;

    /**
     * Class initialisation.
     */
    static
    {
        // Runtime environment.
        RUNTIME = Environment.environment(Sys.ENVIRONMENT);
        LOGGER.info("Set runtime environment to {}", Environment.RUNTIME);

        // Integration environments.
        INTEGRATION_ENVIRONMENTS = new ArrayList<>();

        for (Environment environment : Environment.values())
        {
            if (Environment.TEST != environment && Environment.LOCAL != environment && Environment.INTEGRATION != environment)
            {
                INTEGRATION_ENVIRONMENTS.add(environment);
            }
        }

        LOGGER.info("Integration environments: {}", CollectionUtil.toString(INTEGRATION_ENVIRONMENTS));
    }

    /**
     * Convenience method to get a {@link Environment} with a given environment name, that should match an available "enum" {@link Environment}.
     * @param environment Environment
     * @return Environment or null if "unkown".
     */
    public static Environment environment(String environment)
    {
        for (Environment environmentEnum : values())
        {
            if (environmentEnum.name().equalsIgnoreCase(environment))
            {
                return environmentEnum;
            }
        }

        return null;
    }

    /**
     * Convenience method
     * @param environments Environments
     * @return boolean
     */
    public static boolean isRutime(Environment... environments)
    {
        if (ArrayUtil.contains(environments, Environment.ANY))
        {
            return true;
        }
        else if (ArrayUtil.contains(environments, Environment.RUNTIME))
        {
            return true;
        }
        else if (ArrayUtil.contains(environments, Environment.INTEGRATION))
        {
            if (INTEGRATION_ENVIRONMENTS.contains(Environment.RUNTIME))
            {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param environments Environments
     * @return int
     */
    public static int highestOrdinal(Environment... environments)
    {
        return sorted(environments).last().ordinal();
    }

    /**
     * Sort the given environments naturally.
     * As Environment is an enum, the sorting is by the ordering that each is declared.
     * @param environments Environments
     * @return Set of Environment
     */
    public static SortedSet<Environment> sorted(Environment... environments)
    {
        SortedSet<Environment> sortedEnvironments = new TreeSet<>();

        if (environments != null && environments.length > 0)
        {
            Collections.addAll(sortedEnvironments, environments);
        }

        return sortedEnvironments;
    }

    /**
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString()
    {
        return displayable();
    }

    /**
     * @return the displayable
     */
    public String displayable()
    {
        return displayable;
    }

    /**
     *
     * @param displayable Displayable
     */
    Environment(String displayable)
    {
        this.displayable = displayable;
    }
}