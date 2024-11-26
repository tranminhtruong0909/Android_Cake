package com.example.cake.Model;

public class User {
    private String uid;
    private String name;
    private String address;
    private String phoneNumber;
    private int age;
    private String email;
    private String role;  // Thêm trường role

    // Constructor
    public User(String uid, String name, String address, String phoneNumber, int age, String email, String role) {
        this.uid = uid;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.email = email;
        this.role = role;  // Gán giá trị cho role
    }

    // Getters và Setters
    public String getUidd() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }  // Getter cho role
    public void setRole(String role) { this.role = role; }  // Setter cho role
}
