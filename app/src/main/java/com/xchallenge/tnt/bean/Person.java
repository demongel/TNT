package com.xchallenge.tnt.bean;

import android.databinding.BaseObservable;

/**
 * created by  shakespace
 * 2019/5/19  22:47
 */
public class Person extends BaseObservable {
    private String name;
    private  int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
