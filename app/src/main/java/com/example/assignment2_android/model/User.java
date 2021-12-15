package com.example.assignment2_android.model;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

//Model user
public class User {
    private String name;
    private String password;
    private String email;
    private int age;
    private String id;
    List<String>randomLeaderName = new ArrayList<>();
    List<Integer>randomAge = new ArrayList<>();

    public User(){

    }

    //Create User
    public User(String name, String password, String email, int age, String id){
        this.name = name;
        this.password = password;
        this.email = email;
        this.age = age;
        this.id = id;
    }

    //Create only 1 admin for the app
    public static User createAdmin(ArrayList<User>adminList){
        String id = UUID.randomUUID().toString();
        String name = "voquochuy";
        String password = "admin104";
        String email = "s3823236@admin.com";
        int age = 21;
        return new User(name, password, email, age, id);
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

    // Random age so that can customize later
    public static int getRandomAge(List<Integer> list){
        for (int i = 1; i < 100; i++){
            list.add(i);
        }

        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    };

    // Generate random user and easy to manage or customize
    public void generateUsers(ArrayList<User>userlist, int size){
        for (int i = 101; i < size; i++){
            String id = UUID.randomUUID().toString();
            String name = VolunteerSite.getRandomLeaderName(randomLeaderName) + i;
            int age = getRandomAge(randomAge);
            String password = "1234";
            String email = name  +"@gmail.com";
            User temp = new User(name,password,email,age,id);
            userlist.add(temp);

        }
        System.out.println("hu ne!!1" + userlist);

    }



    // Check to allow to create the site or not
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
