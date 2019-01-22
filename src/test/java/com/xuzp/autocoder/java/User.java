package com.xuzp.autocoder.java;

/**
 * @author za-xuzhiping
 * @Date 2019/1/22
 * @Time 10:02
 */
public class User {
    private String id;
    private String name;
    private long created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
