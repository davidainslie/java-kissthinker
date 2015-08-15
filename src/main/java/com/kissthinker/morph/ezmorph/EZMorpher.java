package com.kissthinker.morph.ezmorph;

import java.util.List;
import java.util.Set;
import net.sf.ezmorph.MorphUtils;
import net.sf.ezmorph.MorpherRegistry;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kissthinker.factory.Factory;
import com.kissthinker.morph.Morpher;
import static com.kissthinker.collection.list.ListUtil.arrayList;
import static com.kissthinker.collection.set.SetUtil.hashSet;
import static com.kissthinker.object.ClassUtil.name;

/**
 * @author David Ainslie
 *
 */
public class EZMorpher implements Morpher
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(Morpher.class);

    /** */
    private final MorpherRegistry morpherRegistry = new MorpherRegistry();

    /** Packages to look up "EZ morphers", set via -Dmorpher.packages (a comma delimited list). Here we always include "com.kissthinker" */
    private final List<String> morpherPackages = arrayList("com.kissthinker");

    /**
     *
     */
    public EZMorpher()
    {
        super();
        registerMorphers();
    }

    /**
     *
     * @see com.kissthinker.morph.Morpher#morph(java.lang.Object, java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <O> O morph(Object object, Class<O> toClass)
    {
        if (object == null)
        {
            LOGGER.warn("Will not attempt to morph the given null object, and so returning null.");
            return null;
        }

        try
        {
            return (O)morpherRegistry.morph(toClass, object);
        }
        catch (Exception e)
        {
            LOGGER.warn("Could not morph {} to required class {}, and so returning null.", name(object), name(toClass));
            return null;
        }
    }

    /** */
    private void registerMorphers()
    {
        MorphUtils.registerStandardMorphers(morpherRegistry);
        registerCustomMorphers(morpherRegistry);
    }

    /**
     *
     * @param morpherRegistry
     */
    private void registerCustomMorphers(MorpherRegistry morpherRegistry)
    {
        LOGGER.info("Registering custom EZ morphers...");

        // TODO Allow for properites files as well as -D?
        if (System.getProperty("morpher.packages") != null)
        {
            for (String morpherPackage : System.getProperty("morpher.packages").split(","))
            {
                morpherPackages.add(morpherPackage);
            }
        }

        Set<Class<? extends net.sf.ezmorph.Morpher>> morpherClasses = hashSet();

        for (String aPackage : morpherPackages)
        {
            Reflections reflections = new Reflections(aPackage);
            morpherClasses.addAll(reflections.getSubTypesOf(AbstractObjectMorpher.class));
        }

        for (Class<? extends net.sf.ezmorph.Morpher> morpherClass : morpherClasses)
        {
            morpherRegistry.registerMorpher(Factory.<net.sf.ezmorph.Morpher>create(morpherClass));
        }
    }
}