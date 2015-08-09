package com.kissthinker.proxy;

/**
 * @author David Ainslie
 *
 */
public interface Proxy
{   
    /**
     * Create (instantiate) an object for the given class which is wrapped within a {@link ClassInfo}.<br/>
     * Instead of simply passing in one argument of {@link Class} to be used as the blueprint for creating a proxy object (which was actually the way it was initially coded),
     * a wrapper is used for any future additions/enhancements to {@link ClassInfo} allowing client code to access future "extras" but without changing the method contract.
     * @param classInfo
     * @return O a proxy for the given (wrapped) class
     */
    <O, P> O create(ClassInfo<O, P> classInfo);

    /**
     *
     * @author David Ainslie
     *
     */
    public static class Null implements Proxy
    {
        /**
         *
         * @see com.kissthinker.proxy.Proxy#create(java.lang.Class)
         */
        @Override
        public <O, P> O create(ClassInfo<O, P> classInfo)
        {
            return null;
        }
    }

    /**
     * 
     * @author David Ainslie
     *
     * @param <O>
     * @param <P>
     */
    public static class ClassInfo<O, P>
    {
        /** */
        private final Class<O> proxyClass;
        
        /** */
        private Class<P> parentClass;
        
        /** */
        private P parent;

        /**
         * @param proxyClass
         */
        public ClassInfo(Class<O> proxyClass)
        {
            super();
            this.proxyClass = proxyClass;
        }

        /**
         * Getter
         * @return the proxyClass
         */
        public Class<O> proxyClass()
        {
            return proxyClass;
        }

        /** Getter
         * @return the parentClass
         */
        public Class<P> parentClass()
        {
            return parentClass;
        }

        /**
         * Setter
         * @param parentClass the parentClass to set
         * @return ClassInfo<O, P>
         */
        public ClassInfo<O, P> parentClass(Class<P> parentClass)
        {
            this.parentClass = parentClass;
            return this;
        }

        /**
         * Getter
         * @return the parent
         */
        public P parent()
        {
            return parent;
        }

        /**
         * Setter
         * @param parent the parent to set
         * @return ClassInfo<O, P>
         */
        public ClassInfo<O, P> parent(P parent)
        {
            this.parent = parent;
            return this;
        }
    }
}