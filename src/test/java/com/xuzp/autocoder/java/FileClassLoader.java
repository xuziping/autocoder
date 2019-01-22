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

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class clazz = null;//this.findLoadedClass(name); // 父类已加载
        //if (clazz == null) {	//检查该类是否已被加载过
        try {
            byte[] classData = FileUtils.readFileToByteArray(new File(classNameToPath(name)));	//根据类的二进制名称,获得该class文件的字节码数组
            if (classData == null) {
                throw new ClassNotFoundException();
            }
            clazz = defineClass(name, classData, 0, classData.length);	//将class的字节码数组转换成Class类的实例
            return clazz;
        }catch(Exception e){
            throw new ClassNotFoundException("Failed to load class, " + name);
        }
    }

    private String classNameToPath(String name) {
        if(rootUrl.endsWith("/")){
            return rootUrl + name.replace(".", "/") + ".class";
        }
        return rootUrl + "/" + name.replace(".", "/") + ".class";
    }

}
