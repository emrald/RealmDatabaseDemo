package com.trivediinfoway.realmdatabasedemo;

import io.realm.RealmObject;

/**
 * Created by TI A1 on 29-03-2018.
 */

public class DataClass extends RealmObject{

    boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;
}
