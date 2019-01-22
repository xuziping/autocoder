package com.xuzp.autocoder.java;

/**
 * @author za-xuzhiping
 * @Date 2019/1/22
 * @Time 10:01
 */
public interface BeanProxy {
    void setDirty(boolean dirty);

    boolean isDirty();
}
