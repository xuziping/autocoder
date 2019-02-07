package com.xuzp.autocoder.java;

import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * @author za-xuzhiping
 * @Date 2019/1/22
 * @Time 14:49
 */
public class FileClassLoader extends ClassLoader {

    private String rootUrl;

    public FileClassLoader(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException
    {
        synchronized (getClassLoadingLock(name)) {
                long t0 = System.nanoTime();
                    // If still not found, then invoke findClass in order
                    // to find the class.
            long t1 = System.nanoTime();
            Class<?> c = findClass(name);
            if(c==null) {
                return super.loadClass(name, resolve);
            }
            // this is the defining class loader; record the stats
            sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
            sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
            sun.misc.PerfCounter.getFindClasses().increment();
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class clazz = null;//this.findLoadedClass(name); // 父类已加载
        //if (clazz == null) {	//检查该类是否已被加载过
        File clsFile = new File(classNameToPath(name));
        if(clsFile.exists() && clsFile.isFile()) {
            try {
                byte[] classData = FileUtils.readFileToByteArray(clsFile);	//根据类的二进制名称,获得该class文件的字节码数组
                if (classData == null) {
                    throw new ClassNotFoundException();
                }
                clazz = defineClass(name, classData, 0, classData.length);	//将class的字节码数组转换成Class类的实例
                return clazz;
            }catch(Exception e){
                throw new ClassNotFoundException("Failed to load class, " + name);
            }
        }
        return null;
    }

    private String classNameToPath(String name) {
        if(rootUrl.endsWith("/")){
            return rootUrl + name.replace(".", "/") + ".class";
        }
        return rootUrl + "/" + name.replace(".", "/") + ".class";
    }

}
