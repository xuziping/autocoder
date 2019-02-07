package com.xuzp.autocoder.java;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.python.google.common.collect.Maps;

import java.io.File;
import java.util.Map;

/**
 * @author za-xuzhiping
 * @Date 2019/1/22
 * @Time 14:10
 */
@Slf4j
public class FlyCommand {

    private static volatile Map<String, Class> classMap = Maps.newHashMap();

    public static void main(String[] args) throws Exception {
        File xx = new File("D://TestFly.java");
        FileUtils.writeStringToFile(xx, "public class TestFly{\n public final static int bbb=0;\n}}", "UTF-8");
        if(new File("D://TestFly.class").exists()) {
            new File("D://TestFly.class").delete();
        }
        Runtime.getRuntime().exec("javac D://TestFly.java");
        Thread.sleep(1000L);

        FileClassLoader ncl1 = new FileClassLoader("D://");
        Thread.sleep(1000L);
        Class pp = ncl1.loadClass("TestFly");
        classMap.put("TestFly", pp);
        log.info("{}", classMap.get("TestFly"));


        FileUtils.writeStringToFile(xx, "public class TestFly{\n public final static int aaa=0;\n}", "UTF-8");
        Runtime.getRuntime().exec("javac D://TestFly.java");
        FileClassLoader nc22 = new FileClassLoader("D://");

        Class pp2 = nc22.loadClass("TestFly");
        classMap.put("TestFly", pp2);
        log.info("{}", classMap.get("TestFly"));
    }
}
