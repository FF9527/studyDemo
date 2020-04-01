package com.app.test.yaml;

/**
 * @author:wuqi
 * @date:2020/3/24
 * @description:com.app.test.yaml
 * @version:1.0
 */
public class Dog {

    private String dogName;
    private Integer age;

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "dogName='" + dogName + '\'' +
                ", age=" + age +
                '}';
    }
}
