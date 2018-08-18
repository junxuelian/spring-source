package com.uestc.bean;

import org.springframework.beans.factory.annotation.Value;

public class Person {

    //使用Value赋值
    //1、基本数值
    //2、可以写SpEl， #{}
    //3、可以写${}，取出配置文件的值（在运行环境变量的值）
    @Value("张三")
    private String name;
    @Value("#{20 - 2}")
    private Integer age;

    @Value("${person.nickName}")
    private String nickName;

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public String getNickName() {
        return nickName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", nickName='" + nickName + '\'' +
                '}';
    }

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
