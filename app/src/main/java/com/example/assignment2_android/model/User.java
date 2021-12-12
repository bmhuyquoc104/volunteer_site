package com.example.assignment2_android.model;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class User {
    private String name;
    private String password;
    private String email;
    private int age;
    private String id;


    public User(){

    }

    public User(String name, String password, String email, int age, String id){
        this.name = name;
        this.password = password;
        this.email = email;
        this.age = age;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void checkAge (int age, Context context){
      if (age < 18) {
          Toast.makeText(context,"You are too young to become a leader of the site",Toast.LENGTH_LONG).show();
      }
    };

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", id='" + id + '\'' +
                '}';
    }
}
