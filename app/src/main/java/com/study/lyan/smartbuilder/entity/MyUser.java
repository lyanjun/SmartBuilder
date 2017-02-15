package com.study.lyan.smartbuilder.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by Lyan on 17/2/11.
 * 用户属性
 */

public class MyUser extends BmobUser{
   private int age;//年龄
   private boolean sex;//性别
   private String desc;//简介

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
