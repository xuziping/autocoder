package com.xuzp.autocoder.java;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @author XuZiPing
 * @Date 2019/1/27
 * @Time 17:11
 */
@Slf4j
public abstract class CachedClassLoader extends ClassLoader {
    protected HashMap<String,Class<?>> cache = null;
    protected String classname;
    protected String path;

    public String getPath() {
        return path;
    }

    public CachedClassLoader(String path, String classname) {
        super();
        this.classname = classname;
        this.path = path;
        this.cache = new HashMap<String,Class<?>>();
    }

    /**
     * Loads the class with the specified name.
     */
    public synchronized Class<?> loadClass(String classname, boolean resolve) {
        if (this.cache.containsKey(classname)) {
            log.debug("load Class:" + classname + " from cache.");
            Class<?> c =  this.cache.get(classname);
            if (resolve)
                resolveClass(c);
            return c;
        } else {
            try {
                Class<?> c = Class.forName(classname);
                return c;
            }
            catch (ClassNotFoundException e) {
                Class<?> c = this.newClass(classname);
                if (c == null)
                    return null;
                this.cache.put(classname, c);
                if (resolve)
                    resolveClass(c);
                return c;
            }
            catch (NoClassDefFoundError e) {
                Class<?> c = this.newClass(classname);
                if (c == null)
                    return null;
                this.cache.put(classname, c);
                if (resolve)
                    resolveClass(c);
                return c;
            }
        }
    }

    public synchronized Class<?> getClass(String classname){
        return this.cache.get(classname);
    }

    /**
     * @return java.lang.Class
     * @param resolve
     */
    public synchronized Class<?> loadClass(boolean resolve) {
        return this.loadClass(this.classname, resolve);
    }

    /**
     * Abstract method for create new class object.
     * @param classname
     * @return
     */
    abstract Class<?> newClass(String classname);

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}
