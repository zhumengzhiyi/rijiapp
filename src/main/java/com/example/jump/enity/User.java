package com.example.jump.enity;

public class User {
    public int id;
    public String name;
    public String shouji;
    public String banji;
    public User(int id, long l, long shouji, long banji){

    }

    public User(int id, String name, String shouji, String banji) {
        this.id = id;
        this.name = name;
        this.shouji = shouji;
        this.banji = banji;
    }

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shouji='" + shouji + '\'' +
                ", banji='" + banji + '\'' +
                '}';
    }
}
