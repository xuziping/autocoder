package com.xuzp.autocoder.java;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * @author za-xuzhiping
 * @Date 2019/1/22
 * @Time 14:10
 */
@Slf4j
public class FlyCommand {

    public static void main(String[] args) throws Exception{
        File xx = new File("D://TestFly.java");
        FileUtils.writeStringToFile(xx, "public class TestFly{}", "UTF-8");
        
        Runtime.getRuntime().exec("javac D://TestFly.java");
        FileClassLoader ncl1 = new FileClassLoader("D://");
        Class pp = ncl1.loadClass("TestFly");
        log.info("{}", pp);

        FileUtils.writeStringToFile(xx, "public class TestFly{\n public static int aaa=0;\n}", "UTF-8");
        Runtime.getRuntime().exec("javac D://TestFly.java");
        Class pp2 = ncl1.loadClass("TestFly");
        log.info("{}", pp2);

    }
}
