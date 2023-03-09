package org.legalperson;

import java.util.Date;

public class Person {
    String id ;
    String name;

    Integer age;

    String copAddr;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public String getCopAddr() {
        return copAddr;
    }

    public void setCopAddr(String copAddr) {
        this.copAddr = copAddr;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", copAddr='" + copAddr + '\'' +
                '}';
    }
}
